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

package it.bioagri.api.auth;

import it.bioagri.models.UserRole;
import it.bioagri.persistence.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/auth")
public final class Auth {

    private final AuthToken authToken;
    private final DataSource dataSource;

    @Autowired
    public Auth(AuthToken authToken, DataSource dataSource) {
        this.authToken = authToken;
        this.dataSource = dataSource;
    }


    //@PostMapping("authenticate")
    @GetMapping("authenticate")
    public ResponseEntity<AuthToken> authenticate(HttpSession session,
                                                  @RequestParam(required = false) String username,
                                                  @RequestParam(required = false) String password) {

        if(username == null || username.isEmpty())
            throw new AuthFailedException("username can not be null or empty");

        if(password == null || password.isEmpty())
            throw new AuthFailedException("password can not be null or empty");


        // if(!validateUser(username, encryptedPassword))
        //     throw new AuthFailedException("username/password wrong");

        if(authToken.isExpired())
            authToken.generateToken(1L, UserRole.CUSTOMER); // TODO: get userId and userRole

        return new ResponseEntity<>(authToken, HttpStatus.OK);

    }

    @RequestMapping("disconnect")
    public void disconnect(HttpSession session) {

        authToken.setToken(null);
        authToken.setTimestamp(null);

        session.invalidate();

    }

}