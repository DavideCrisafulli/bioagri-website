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

<!DOCTYPE html>
<html lang="it">

    <head>

        <meta charset="UTF-8" />
        <meta name="description" content="" />  <!-- TODO! -->
        <meta name="author" content="Antonino Natale, Matteo Perfidio, Davide Crisafulli" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no" />
        <meta name="theme-color" content="rgb(236, 104, 81)" /> <!-- TODO! -->
        <meta name="msapplication-navbutton-color" content="rgb(236, 104, 81)" />  <!-- TODO! -->
        <meta name="apple-mobile-web-app-status-bar-style" content="rgb(236, 104, 81)" />  <!-- TODO! -->

        <meta property="og:title" content="Bioagri Shop" /> <!-- TODO! -->
        <meta property="og:description" content="" />  <!-- TODO! -->
        <meta property="og:type" content="website" />
        <meta property="og:url" content="https://www.bioagrishop.it" />
        <meta property="og:image" content="/assets/favicon.png" />

        <link rel="profile" href="http://gmpg.org/xfn/11">
        <link rel="icon" href="/assets/favicon.png">

        <title>Bioagri Shop</title>


        <!-- Styles -->
        <link rel="stylesheet" href="/assets/css/normalize.css">
        <link rel="stylesheet" href="/assets/css/bootstrap/bootstrap-reboot.min.css">
        <link rel="stylesheet" href="/assets/css/bootstrap/bootstrap.min.css">
        <!--<link rel="stylesheet" href="/assets/css/bootstrap/bootstrap-grid.min.css">-->
        <link rel="stylesheet" href="/assets/css/now-ui-kit/now-ui-kit.min.css">

        <!-- Third-party Dependencies -->
        <script src="/assets/js/third-party/jquery.min.js"></script>
        <script src="/assets/js/third-party/popper.min.js"></script>
        <script src="/assets/js/third-party/js.cookie.min.js"></script>
        <script src="/assets/js/third-party/bootstrap/bootstrap.min.js"></script>
        <script src="/assets/js/third-party/now-ui-kit/now-ui-kit.min.js"></script>
        <script src="/assets/js/third-party/now-ui-kit/plugins/nouislider.min.js"></script>
        <script src="/assets/js/third-party/now-ui-kit/plugins/bootstrap-datepicker.js"></script>
        <script src="/assets/js/third-party/now-ui-kit/plugins/bootstrap-switch.js"></script>

        <!-- Dependencies -->
        <script src="/assets/js/ui/api.js"></script>
        <script src="/assets/js/ui/components.js"></script>

    </head>

    <body>
        <jsp:include page="${reference}" />
    </body>

</html>