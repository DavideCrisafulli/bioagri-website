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

'use strict';


const $baseUri = 'http://localhost:8080';
const $basePath = '/api';


/**
 * Make a request to back-end and retrive, add, edit and delete data.
 * @param path {string}
 * @param method {string}
 * @param body {object}
 * @param returnType {string}
 * @returns {Promise<Response | object | object[] | void>}
 */
const api = async (path, method = 'GET', body = {}, returnType = 'json') => {

    return fetch($baseUri + $basePath + encodeURI(path), {

        method: method,
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'include',
        redirect: 'follow',
        referrerPolicy: 'no-referrer',

        headers: {
            'X-Auth-Token': sessionStorage.getItem('X-Auth-Token'),
            'Content-Type': 'application/json',
            'Accept'      : 'application/json',
        },

        body: method !== 'GET' ? JSON.stringify(body) : null

    }).then(response => {

        if(response.headers.has('X-Auth-Token'))
            sessionStorage.setItem('X-Auth-Token', response.headers.get('X-Auth-Token'));

        if(response.status < 200 || response.status > 299)
            throw response.status;


        if(returnType === 'json')
            return response.json();

        if(returnType === 'text')
            return response.text();

        return response;

    });

}

