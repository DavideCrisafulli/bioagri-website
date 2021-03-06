<%@ page import="it.bioagri.api.auth.AuthToken" %><%--
  ~ MIT License
  ~
  ~ Copyright (c) 2020 BioAgri S.r.l.s.
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a copy
  ~ of this software and associated documentation files (the "Software"), to deal
  ~ in the Software without restriction, including without limitation the rights
  ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~ copies of the Software, and to permit persons to whom the Software is
  ~ furnished to do so, subject to the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be included in all
  ~ copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  ~ SOFTWARE.
  ~
  --%>

<%--@elvariable id="components" type="java.util.Map"--%>
<%--@elvariable id="locale" type="java.util.Map"--%>
<%--@elvariable id="reference" type="java.lang.String"--%>
<%--@elvariable id="authToken" type="it.bioagri.api.auth.authToken"--%>



<section id="ui-navigation-container" ui-title="${locale.page_registration} &ndash; ${locale.info_title}">


    <!-- Navigation Bar -->
    <ui-navbar id="ui-navbar"></ui-navbar>


    <section ui-animated>


        <br>
        <br>
        <br>
        <br>
        <br>
        <br>

        <div class="ui-container text-dark">
            <div class="bg-light shadow border w-100 w-lg-50 mx-auto my-5" ui-animated="backInUp">
                <div class="my-5">

                    <!-- Registration Logo -->
                    <div class="w-100 py-4">
                        <ui-image id="ui-registration-logo-image"
                                  ui:src="${locale.nav_logo}"
                                  ui:width="100%"
                                  ui:height="64px"
                                  ui:position="center"
                                  ui:size="contain"></ui-image>
                    </div>

                    <!-- Registration Slogan -->
                    <div class="text-center">
                        <h4 class="ui-font-title-primary display-6">${locale.registration_slogan_title}</h4>
                        <p class="ui-font-title-secondary fs-5">${locale.registration_slogan_subtitle}</p>
                    </div>

                    <div class="p-5">

                        <!-- Registration Form -->
                        <ui-registration id="ui-registration"></ui-registration>

                    </div>

                </div>
            </div>
        </div>

        <br>
        <br>
        <br>
        <br>

    </section>

    <!-- Footer -->
    <ui-footer id="ui-footer" ui:current="home"> </ui-footer>

</section>


