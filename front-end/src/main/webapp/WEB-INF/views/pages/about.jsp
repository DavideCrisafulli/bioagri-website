<%--
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

<jsp:include page="/WEB-INF/components/common/navbar/navbar.controller.jsp" />
<jsp:include page="/WEB-INF/components/common/header/header.controller.jsp" />
<jsp:include page="/WEB-INF/components/common/footer/footer.controller.jsp" />

<section id="ui-navigation-container">

    <!-- Header -->
    <ui-header id="ui-header"></ui-header>

    <!-- Navigation Bar -->
    <ui-navbar id="ui-navbar" ui:current="about"></ui-navbar>

    <!-- About -->
    <section ui-animated>

        <div class="ui-container ui-about">

            <!-- About Header -->
            <div class="row align-items-center">

                <div class="col-md">
                    <ul>
                        <li>
                            <h6 class="ui-about_header_title"> ${ locale.about_header_title } </h6>
                            <h1 class="ui-about_header_subtitle"><span > ${ locale.about_header_subtitle_start } </span><b> ${ locale.about_header_subtitle_end } </b></h1>
                            <img class="ui-about_leaf" src="/assets/img/about/decor.jpg">
                            <p> ${ locale.about_header_body } </p>
                        </li>
                    </ul>
                </div>

                <div class="col-md text-center">
                    <img class="ui-rounded_image" src="/assets/img/about/img_rounded.jpg">
                </div>

            </div>

            <br>
            <br>

            <!-- About History -->
            <div class="row align-items-center text-center ui-history">

                <div class="ui-history-header">

                    <header>${ locale.history_subtitle }</header>
                    <h1>${ locale.history_title }</h1>

                    <br>
                    <br>

                    <img class="ui-history-image" src="/assets/img/about/history.jpg">
                    <div class="ui-history-divider"></div>

                </div>






            </div>


        </div>


    </section>

    <!-- Footer -->
    <ui-footer id="ui-footer" ui:current="home"> </ui-footer>

</section>