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

package it.bioagri.api.tickets;


import it.bioagri.api.ApiPermission;
import it.bioagri.api.ApiPermissionOperation;
import it.bioagri.api.ApiPermissionType;
import it.bioagri.api.ApiResponseStatus;
import it.bioagri.api.auth.AuthToken;
import it.bioagri.models.Ticket;
import it.bioagri.persistence.DataSource;
import it.bioagri.persistence.DataSourceSQLException;
import it.bioagri.utils.ApiRequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class Tickets {

    private final AuthToken authToken;
    private final DataSource dataSource;

    @Autowired
    public Tickets(AuthToken authToken, DataSource dataSource) {
        this.authToken = authToken;
        this.dataSource = dataSource;
    }


    @GetMapping("")
    public ResponseEntity<List<Ticket>> findAll(
            @RequestParam(required = false, defaultValue =   "0") Long skip,
            @RequestParam(required = false, defaultValue = "999") Long limit,
            @RequestParam(required = false, value =  "filter-by") List<String> filterBy,
            @RequestParam(required = false, value = "filter-val") List<String> filterValue,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, value =  "sorted-by") String sortedBy) {

        try {

            return ResponseEntity.ok(
                    dataSource.getTicketDao()
                            .findAll()
                            .stream()
                            .filter(i -> ApiPermission.hasPermission(ApiPermissionType.TICKETS, ApiPermissionOperation.READ, authToken, i.getUserId()))
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
    public ResponseEntity<Ticket> findById(@PathVariable Long id) {

        try {

            return ResponseEntity.ok(dataSource.getTicketDao()
                    .findByPrimaryKey(id)
                    .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.TICKETS, ApiPermissionOperation.READ, authToken, i.getUserId()))
                    .orElseThrow(() -> new ApiResponseStatus(404)));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody Ticket ticket) {

        ApiPermission.verifyOrThrow(ApiPermissionType.TICKETS, ApiPermissionOperation.CREATE, authToken, ticket.getUserId());

        try {

            ticket.setId(dataSource.getId("shop_ticket", Long.class));

            dataSource.getTicketDao().save(ticket);

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(URI.create("/api/tickets/%d".formatted(ticket.getId()))).build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Ticket ticket) {

        try {

            ticket.setId(dataSource.getId("shop_ticket", Long.class));

            dataSource.getTicketDao().findByPrimaryKey(id)
                    .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.TICKETS, ApiPermissionOperation.UPDATE, authToken, i.getUserId()))
                    .ifPresentOrElse(
                            (r) -> dataSource.getTicketDao().update(r, ticket),
                            ( ) -> dataSource.getTicketDao().save(ticket)
                    );

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(URI.create("/api/tickets/%d".formatted(id))).build();

    }


    @DeleteMapping("")
    public ResponseEntity<String> deleteAll(
            @RequestParam(required = false, value =  "filter-by") List<String> filterBy,
            @RequestParam(required = false, value = "filter-val") List<String> filterValue) {

        try {

            dataSource.getTicketDao()
                    .findAll()
                    .stream()
                    .filter(i -> ApiPermission.hasPermission(ApiPermissionType.TICKETS, ApiPermissionOperation.DELETE, authToken, i.getUserId()))
                    .filter(i -> ApiRequestQuery.filterBy(filterBy, filterValue, i, dataSource))
                    .forEach(dataSource.getTicketDao()::delete);

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        try {

            dataSource.getTicketDao().delete(
                    dataSource.getTicketDao()
                            .findByPrimaryKey(id)
                            .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.TICKETS, ApiPermissionOperation.DELETE, authToken, i.getUserId()))
                            .orElseThrow(() -> new ApiResponseStatus(404)));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();

    }

}
