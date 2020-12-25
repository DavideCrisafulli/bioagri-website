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
<jsp:include page="/WEB-INF/components/common/breadcrumb/breadcrumb.controller.jsp"/>
<jsp:include page="/WEB-INF/components/image/parallax/parallax.controller.jsp"/>


<section id="ui-navigation-container" ui-title="${locale.page_support} &ndash; ${locale.info_title}">

    <!-- Header -->
    <ui-header id="ui-header"></ui-header>

    <!-- Navigation Bar -->
    <ui-navbar id="ui-navbar" ui:current="support"></ui-navbar>

    <!-- Support -->
    <section ui-animated>

        <!-- Support Header -->
        <ui-parallax id="ui-parallax-3" ui:src="/assets/img/about/header.jpg" ui:reserve="300px">
            <div class="ui-container">

                <div class="row">

                    <div class="col-auto ui-support-header">
                        <h1 class="display-6 pt-3">${ locale.support_title }</h1>
                    </div>

                </div>

            </div>
        </ui-parallax>

        <br>
        <br>

        <div class="ui-container">

            <!-- Breadcrumb -->
            <ui-breadcrumb id="ui-breadcrumb-1" ui:current="${locale.page_support}"></ui-breadcrumb>

        </div>

        <br>
        <br>

        <div class="ui-container">

            <!-- Contact Info -->
            <section>
                <div class="ui-container">

                    <!-- Contact Info Head-->
                    <div class="row">

                        <div class="col-12 ui-support-contact-info">

                            <div class="text-center">
                                <h6 class="display-8"> ${ locale.support_contact_info_title } </h6>
                                <h1 class="display-5"><span> ${ locale.support_contact_info_subtitle_start } </span><b> ${ locale.support_contact_info_subtitle_end } </b></h1>
                                <img class="mt-3" src="/assets/img/support/decorator.jpg">
                            </div>

                        </div>

                    </div>

                    <br>
                    <br>

                    <!-- Contact Info Body-->
                    <div class="row">

                        <div class="col-12 col-md-4">
                            <div class="text-center">

                                <div class="ui-support-contact-info-icon rounded-circle" ui-animated-hover ui-animated-scroll>
                                    <span class="mdi mdi-google-maps mdi-36px"></span>
                                </div>

                                <h4>${ locale.support_contact_info_address }</h4>
                                <p>${ locale.info_address }</p>

                            </div>
                        </div>

                        <div class="col-12 col-md-4">
                            <div class="text-center">

                                <div class="ui-support-contact-info-icon rounded-circle" ui-animated-hover ui-animated-scroll>
                                    <span class="mdi mdi-email mdi-36px"></span>
                                </div>

                                <h4>${ locale.support_contact_info_phone }</h4>
                                <p>${ locale.info_phone }</p>

                            </div>
                        </div>

                        <div class="col-12 col-md-4">
                            <div class="text-center">

                                <div class="ui-support-contact-info-icon rounded-circle" ui-animated-hover ui-animated-scroll>
                                    <span class="mdi mdi-phone mdi-36px"></span>
                                </div>

                                <h4>${ locale.support_contact_info_email }</h4>
                                <p>${ locale.info_email }</p>

                            </div>
                        </div>

                    </div>

                </div>
            </section>

            <br>
            <br>
            <br>
            <br>

            <!-- Contact Form -->
            <section>
                <div class="row">
                    <div class="col-md">

                        <div class="ui-support-form-header">
                            <h6 class="display-8"> ${ locale.support_form_title } </h6>
                            <h1 class="display-5"><span> ${ locale.support_form_subtitle_start } </span><b> ${ locale.support_form_subtitle_end } </b></h1>
                            <img class="mt-3" src="/assets/img/support/decorator2.jpg">
                        </div>

                        <br>

                        <div>
                            <form action="#" method="post">
                                <div class="row">

                                    <div class="col-lg-6 mt-3">
                                        <input type="text" class="form-control" name="name" placeholder="${ locale.support_form_name }">
                                    </div>

                                    <div class="col-lg-6 mt-3">
                                        <input type="text" class="form-control" name="surname" placeholder="${ locale.support_form_surname }">
                                    </div>

                                    <div class="col-lg-6 mt-3">
                                        <input type="email" class="form-control" name="email" placeholder="${ locale.support_form_email }">
                                    </div>

                                    <div class="col-lg-6 mt-3">
                                        <input type="tel" class="form-control" name="phone" placeholder="${ locale.support_form_phone }">
                                    </div>

                                    <div class="col-12 mt-3">
                                        <input type="text" class="form-control" name="subject" placeholder="${ locale.support_form_subject }">
                                    </div>

                                    <div class="col-12 mt-3">
                                        <textarea name="message" class="form-control" cols="30" rows="8" placeholder="${ locale.support_form_message }"></textarea>
                                    </div>

                                    <div class="col-12 mt-3">
                                        <button type="submit">${ locale.support_form_submit }</button>
                                    </div>

                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </section>

            <br>
            <br>
            <br>

            <!-- Google Maps -->
            <section>
                <div class="row ui-support-maps">

                    <h1 class="display-5 mb-3"> ${ locale.support_maps }</h1>
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d24985.75860010016!2d15.953050639445964!3d38.482569241046406!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x131512757249aded%3A0x4896d3bba66df8d0!2sFarmacia%20Agricola%20S.R.L.!5e0!3m2!1sit!2sit!4v1608935378305!5m2!1sit!2sit" width="960" height="600" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>

                </div>
            </section>

        </div>

    </section>

    <br>
    <br>
    <br>
    <br>
    <br>

    <!-- Footer -->
    <ui-footer id="ui-footer" ui:current="home"> </ui-footer>

</section>