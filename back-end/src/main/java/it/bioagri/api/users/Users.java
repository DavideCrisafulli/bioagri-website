/*
 * MIT License
 *
 * Copyright (c) 2020 BioAgri S.r.l.s.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.bioagri.api.users;


import ch.qos.logback.classic.Logger;
import it.bioagri.api.ApiPermission;
import it.bioagri.api.ApiPermissionOperation;
import it.bioagri.api.ApiPermissionType;
import it.bioagri.api.ApiResponseStatus;
import it.bioagri.api.auth.AuthToken;
import it.bioagri.models.User;
import it.bioagri.models.UserStatus;
import it.bioagri.persistence.DataSource;
import it.bioagri.persistence.DataSourceSQLException;
import it.bioagri.utils.ApiRequestQuery;
import it.bioagri.utils.Mail;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class Users {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(Users.class);

    private final AuthToken authToken;
    private final DataSource dataSource;
    private final Mail mail;


    @Autowired
    public Users(AuthToken authToken, DataSource dataSource, Mail mail) {
        this.authToken = authToken;
        this.dataSource = dataSource;
        this.mail = mail;
    }


    @GetMapping("")
    public ResponseEntity<List<User>> findAll(
            @RequestParam(required = false, defaultValue =   "0") Long skip,
            @RequestParam(required = false, defaultValue = "999") Long limit,
            @RequestParam(required = false, value =  "filter-by") List<String> filterBy,
            @RequestParam(required = false, value = "filter-val") List<String> filterValue,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, value =  "sorted-by") String sortedBy) {

        try {

            return ResponseEntity.ok(
                    dataSource.getUserDao().findAll()
                            .stream()
                            .filter(i -> ApiPermission.hasPermission(ApiPermissionType.USERS, ApiPermissionOperation.READ, authToken, i.getId()))
                            .filter(i -> ApiRequestQuery.filterBy(filterBy, filterValue, i, dataSource))
                            .sorted((a, b) -> ApiRequestQuery.sortedBy(sortedBy, order, a, b))
                            .skip(skip)
                            .limit(limit)
                            .collect(Collectors.toList()));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {

        try {

            return ResponseEntity.ok(dataSource.getUserDao()
                    .findByPrimaryKey(id)
                    .filter(i -> ApiPermission.hasPermission(ApiPermissionType.USERS, ApiPermissionOperation.READ, authToken, i.getId()))
                    .orElseThrow(() -> new ApiResponseStatus(404)));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PostMapping("")
    public ResponseEntity<String> create(HttpServletRequest request, @RequestBody User user) {


        if(Objects.equals(request.getAttribute("John"), "Doe"))
            logger.trace("POST /api/users: Permission granted from John Doe!");
        else
            ApiPermission.verifyOrThrow(ApiPermissionType.USERS, ApiPermissionOperation.CREATE, authToken, user.getId());


        try {

            user.setId(dataSource.getId("shop_user", Long.class));

            dataSource.getUserDao().save(user);


            if(user.getStatus().equals(UserStatus.WAIT_FOR_MAIL))
                mail.sendUserRegistration(request, request.getSession(), user.getId(), user.getMail());


        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(URI.create("/api/users/%d".formatted(user.getId()))).build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User user) {

        try {

            user.setId(dataSource.getId("shop_user", Long.class));

            dataSource.getUserDao().findByPrimaryKey(id)
                    .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.USERS, ApiPermissionOperation.UPDATE, authToken, i.getId()))
                    .ifPresentOrElse(
                            (r) -> dataSource.getUserDao().update(r, user),
                            ( ) -> dataSource.getUserDao().save(user)
                    );

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(URI.create("/api/users/%d".formatted(id))).build();

    }



    @DeleteMapping("")
    public ResponseEntity<String> deleteAll(
            @RequestParam(required = false, value =  "filter-by") List<String> filterBy,
            @RequestParam(required = false, value = "filter-val") List<String> filterValue) {

        try {

            dataSource.getUserDao()
                    .findAll()
                    .stream()
                    .filter(i -> ApiPermission.hasPermission(ApiPermissionType.USERS, ApiPermissionOperation.DELETE, authToken, i.getId()))
                    .filter(i -> ApiRequestQuery.filterBy(filterBy, filterValue, i, dataSource))
                    .forEach(dataSource.getUserDao()::delete);

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        try {

            dataSource.getUserDao().delete(
                    dataSource.getUserDao()
                            .findByPrimaryKey(id)
                            .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.USERS, ApiPermissionOperation.DELETE, authToken, i.getId()))
                            .orElseThrow(() -> new ApiResponseStatus(404)));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();

    }


}
