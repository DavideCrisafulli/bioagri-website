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


<script defer>

    Component.register('ui-registration', (id, props) => new class extends FormComponent {

        constructor() {
            super(id, {

                username: {
                    type: 'email',
                    label: "Indirizzo email", // FIXME
                    pattern: /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/,
                    required: true,
                    size: 128, // FIXME
                    wrong: "Username wrong! (FIXME)"
                },

                password: {
                    type: 'password',
                    label: "Password", // FIXME
                    minlength: 8,
                    pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,
                    required: true,
                    size: 128,
                },

                name: {
                    type: 'text',
                    label: 'Nome', // FIXME,
                    required: true,
                    maxlength: 32
                },

                surname: {
                    type: 'text',
                    label: 'Cognome', // FIXME,
                    required: true,
                    maxlength: 32
                },

                gender: {
                    type: 'select',
                    label: 'Sesso', // FIXME,
                    required: true,
                    options: [
                        'male',
                        'female',
                        'other',
                        'undefined'
                    ]
                },

                birth: {
                    type: 'date',
                    label: 'Data di nascita', // FIXME
                    required: true,
                },

                phone: {
                    type: 'tel',
                    label: 'Recapito telefonico', // FIXME
                    required: true,
                    size: 16,
                },

                legals: {
                    type: 'switch',
                    label: `${locale.registration_legal}`,
                    required: true
                },

                submitPosition: 'center',

                $submit: `${locale.registration_button}`,

            });
        }

        onReady(state) {
            this.state = { $state: 'need-registration' };
        }

        onSubmit(data) {

        }

    });

</script>
