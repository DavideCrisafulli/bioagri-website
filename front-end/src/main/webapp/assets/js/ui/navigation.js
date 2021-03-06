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


/**
 * Navigate through pages without reloading page.
 * @param url {string}
 * @param data {object}
 * @param container {HTMLElement || string}
 * @param pushState {boolean}
 */
const navigate = (url, data = undefined, container = '#ui-navigation-container', pushState = true) => {

    if(!url)
        throw new Error('URL cannot be null');

    if(data)
        url += '?' + $.param(data);


    if (window.history && window.history.pushState) {

        if(window.history.scrollRestoration)
            window.history.scrollRestoration = 'manual';


        const cont = $('#ui-navigation-progress-box')
            .removeClass('d-none');

        const prog = $('#ui-navigation-progress-bar')
            .css('width', '33%')
            .attr('aria-valuenow', '33');


        fetch(url)
            .then((response) => response.text())
            .then((response) => {

                Component.destroy();

                if (container === document.documentElement) {

                    try {

                        $(container).html($(response).html());

                    } catch (reason) {
                        console.error("Caught an error when navigating on navigation ", url, response, reason);
                    }

                } else {

                    $.each($(response), (i, e) => {

                        try {

                            if ($(e).is('script'))
                                eval($(e).html());

                        } catch (reason) {
                            console.error("Caught an error when running script on ", url, e, reason);
                        }


                        if ($(container).id !== $(e).id)
                            return;

                        try {

                            $(container).html($(e).html());
                            $(container).attr('ui-title', $(e).attr('ui-title'));

                        } catch (reason) {
                            console.error("Caught an error when navigating on navigation ", url, e, reason);
                        }

                    });


                }


                const title = $(container).attr('ui-title') || document.title;

                if(pushState)
                    window.history.replaceState(data, title, url);

                document.title = title;



                prog.css('width', '66%')
                    .attr('aria-valuenow', '66');

                window.scroll(0, 0);


            })
                .then(() => Component.run())
                .then(() => prog.css('width', '100%').attr('aria-valuenow', '100'))
                .then(() => $(document).trigger('ui-ready'))
                .then(() => new Promise(r => setTimeout(r, 1000)))
                .then(() => cont.addClass('d-none'))
                .then(() => prog.css('width', '0').attr('aria-valuenow', '0'))

            .catch(reason => {

                console.error(reason);

                $(container).append(`
                    <div id="ui-navigation-error" class="alert alert-danger alert-dismissible fixed-bottom fade show mx-5" role="alert">
                        <strong>Ops!</strong> Seems there is no internet connection here :(
                        <button type="button" class="btn-close" onclick="$('#ui-navigation-error').alert('close')" aria-label="Close"></button>
                    </div>
                `);

            })


        if(pushState)
            window.history.pushState(data, document.title, url);


    } else {

        console.warn("window.history not supported, fallback to window.location :(");
        window.location.href = url;

    }

}


$(document).ready(() => {

    const container = $('#ui-navigation-container');

    if(!container)
        throw new Error("#ui-navigation-container cannot be null");


    container.on('click', '[ui-navigate]', {}, (e) => {

        e.preventDefault();
        e.stopPropagation();

        if (window.location === this.href)
            return;

        if(e.currentTarget.hasAttribute('ui-data'))
            navigate(e.currentTarget.href, eval('(() => { return ' + e.currentTarget.attributes['ui-data'].value + '})()'));
        else
            navigate(e.currentTarget.href);

    });

    $('body').append(`
        <div id="ui-navigation-progress-box" class="ui-navigation-progress progress bg-white fixed-top d-none">
            <div id="ui-navigation-progress-bar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
        </div>
    `);


    if(container.attr('ui-title'))
        document.title = container.attr('ui-title');

    $(document).trigger('ui-ready');

});



window.pathname = document.location.pathname;

window.onpopstate = (e) => {

    if(window.pathname !== document.location.pathname)
        navigate(document.location.href, e.state, '#ui-navigation-container', false);

    window.pathname = document.location.pathname;

}
