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
import it.bioagri.models.TicketResponse;
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
public class TicketResponses {


    private final AuthToken authToken;
    private final DataSource dataSource;

    @Autowired
    public TicketResponses(AuthToken authToken, DataSource dataSource) {
        this.authToken = authToken;
        this.dataSource = dataSource;
    }


    @GetMapping("/{sid}/responses")
    public ResponseEntity<List<TicketResponse>> findAll(
            @PathVariable Long sid,
            @RequestParam(required = false, defaultValue =   "0") Long skip,
            @RequestParam(required = false, defaultValue = "999") Long limit,
            @RequestParam(required = false, value =  "filter-by") List<String> filterBy,
            @RequestParam(required = false, value = "filter-val") List<String> filterValue,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, value =  "sorted-by") String sortedBy) {

        try {

            return ResponseEntity.ok(dataSource.getTicketResponseDao()
                    .findAll()
                    .stream()
                    .filter(i -> i.getTicketId().equals(sid))
                    .filter(i -> ApiPermission.hasPermission(ApiPermissionType.TICKET_RESPONSES, ApiPermissionOperation.READ, authToken, i.getTicket(dataSource)
                            .orElseThrow(() -> new ApiResponseStatus(502))
                            .getUserId()))
                    .filter(i -> ApiRequestQuery.filterBy(filterBy, filterValue, i, dataSource))
                    .sorted((a, b) -> ApiRequestQuery.sortedBy(sortedBy, order, a, b))
                    .skip(skip)
                    .limit(limit)
                    .collect(Collectors.toList()));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{sid}/responses/{id}")
    public ResponseEntity<TicketResponse> findById(@PathVariable Long sid, @PathVariable Long id) {

        try {

            return ResponseEntity.ok(dataSource.getTicketResponseDao()
                    .findByPrimaryKey(id)
                    .filter(i -> i.getTicketId().equals(sid))
                    .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.TICKET_RESPONSES, ApiPermissionOperation.READ, authToken, i.getTicket(dataSource)
                            .orElseThrow(() -> new ApiResponseStatus(502))
                            .getUserId()))
                    .orElseThrow(() -> new ApiResponseStatus(404)));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PostMapping("/{sid}/responses")
    public ResponseEntity<String> create(@PathVariable Long sid, @RequestBody TicketResponse response) {

        ApiPermission.verifyOrThrow(ApiPermissionType.TICKET_RESPONSES, ApiPermissionOperation.CREATE, authToken, response.getTicket(dataSource)
                .orElseThrow(() -> new ApiResponseStatus(400))
                .getUserId());

        try {

            response.setId(dataSource.getId("shop_ticket_response", Long.class));

            dataSource.getTicketResponseDao().save(response);

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(URI.create("/api/tickets/%d/responses/%d".formatted(sid, response.getId()))).build();

    }


    @PutMapping("/{sid}/responses/{id}")
    public ResponseEntity<String> update(@PathVariable Long sid, @PathVariable Long id, @RequestBody TicketResponse response) {

        try {

            response.setId(dataSource.getId("shop_ticket_response", Long.class));

            dataSource.getTicketResponseDao()
                    .findByPrimaryKey(id)
                    .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.TICKET_RESPONSES, ApiPermissionOperation.UPDATE, authToken, i.getTicket(dataSource)
                            .orElseThrow(() -> new ApiResponseStatus(502))
                            .getUserId()))
                    .ifPresentOrElse(
                            (r) -> dataSource.getTicketResponseDao().update(r, response),
                            ( ) -> dataSource.getTicketResponseDao().save(response)
                    );

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(URI.create("/api/tickets/%d/responses/%d".formatted(sid, response.getId()))).build();

    }


    @DeleteMapping("/{sid}/responses")
    public ResponseEntity<String> deleteAll(
            @PathVariable Long sid,
            @RequestParam(required = false, value =  "filter-by") List<String> filterBy,
            @RequestParam(required = false, value = "filter-val") List<String> filterValue) {


        try {

            dataSource.getTicketResponseDao()
                    .findAll()
                    .stream()
                    .filter(i -> i.getTicketId().equals(sid))
                    .filter(i -> ApiPermission.hasPermission(ApiPermissionType.TICKET_RESPONSES, ApiPermissionOperation.DELETE, authToken, i.getTicket(dataSource)
                            .orElseThrow(() -> new ApiResponseStatus(502))
                            .getUserId()))
                    .filter(i -> ApiRequestQuery.filterBy(filterBy, filterValue, i, dataSource))
                    .forEach(dataSource.getTicketResponseDao()::delete);

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();

    }


    @DeleteMapping("/{sid}/responses/{id}")
    public ResponseEntity<String> delete(@PathVariable Long sid, @PathVariable Long id) {

        try {

            dataSource.getTicketResponseDao().delete(
                    dataSource.getTicketResponseDao()
                            .findByPrimaryKey(id)
                            .filter(i -> i.getTicketId().equals(sid))
                            .filter(i -> ApiPermission.verifyOrThrow(ApiPermissionType.TICKET_RESPONSES, ApiPermissionOperation.DELETE, authToken, i.getTicket(dataSource)
                                    .orElseThrow(() -> new ApiResponseStatus(502))
                                    .getUserId()))
                            .orElseThrow(() -> new ApiResponseStatus(404)));

        } catch (DataSourceSQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();

    }


}
