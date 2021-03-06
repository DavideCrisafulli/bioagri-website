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
 * Collection of component instances.
 * @type {Component[]}
 */
window.components = window.components || [];

/**
 * Collection of registered components.
 * @type {function}
 */
window.registered = window.registered || [];



/**
 * Load registered components when DOM is ready.
 */
$(document).ready(() => {
    Component.run();
});




/**
 * UI Component type.
 */
class Component {

    /**
     * @param elem {HTMLElement}       HTML Element
     */
    constructor(elem) {

        /**
         * Unique HTML ID of a Component.
         * @type {string}
         */
        this.id = `${elem.id}`;

        /**
         * User external innerHTML of a Component.
         * @type {string}
         */
        this.innerHTML = `${elem.innerHTML}`;

        /**
         * Rendered HTML of a Component
         * @type {string}
         */
        this.renderedHTML = '';

        /**
         * Collection map of binds state value in a HTMLElement.
         * @type {{selector: string, value:string}[]}
         * @example ui:bind-*="#app:appInfo.name"
         */
        this.binds = [];

        /**
         * Collection of external event
         * @type {string[]}
         */
        this.events = [];



        if(!this.id || this.id === '')
            throw new Error("Component id cannot be null: " + elem.localName);


        for(const attr of elem.attributes) {

            if(!attr.name.startsWith('ui:bind-'))
                continue;

            ((i) =>
                this.binds.push(Object.create({ selector: i[0], value: i[1] }))
            ) (attr.value.split(':'));

        }

        for(const attr of elem.attributes) {

            if(!attr.name.startsWith('ui:on-'))
                continue;

            this.events[attr.name.slice(6)] = attr.value;

        }

        window.components[this.id] = this;

    }


    /**
     * Return HTML Element of a Component.
     * @return {HTMLElement | Element | null}
     */
    get elem() {
        return document.querySelector('#' + this.id);
    }

    /**
     * Check weather a component is still running.
     * @return {boolean}
     */
    get running() {
        return !!(this.elem);
    }

    /**
     * Render a template string into HTML Element each state update.
     * @returns {string}
     */
    onRender() {
        return "";
    }

    /**
     * Render a template string into HTML Element before loading state.
     * @returns {string}
     */
    onLoading() {
        return ` 
            <div class="d-inline-block w-100 h-100" ui-animated>
                <div class="d-grid align-items-center h-100">
                    <div class="spinner-grow m-auto" role="status">
                        <span class="visually-hidden"></span>
                    </div>
                </div>
            </div>
        `;
    }

    /**
     * Render a template string into HTML Element on loading failure.
     * @returns {string}
     */
    onError() {
        return "There was an error when loading component";
    }

    /**
     * Initialize component's event.
     */
    onInit() {
        this.raise('init');
    }

    /**
     * Destroy component's event.
     */
    onDestroy() {
        this.raise('destroy');
    }


    /**
     * Raise external event
     * @param event {string}
     * @return {boolean | object | null | void}
     * @example ui:on-init="console.debug(this)"
     */
    raise(event) {

        try {

            if (event in this.events)
                return new Function(this.events[event]).apply(this);

        } catch (e) {
            console.error('Unhandled exception on', event, e);
        }

        return false;

    }


    /**
     * Register a component and attach it to their HTML Elements.
     * @param tag {string}
     * @param getInstance {function}
     */
    static register (tag, getInstance) {

        if(!tag)
            throw new Error("tag cannot be null");

        if(!getInstance)
            throw new Error("getinstance cannot be null");


        window.registered[tag] = getInstance;

        console.debug("Registered component: ", tag);

    }


    /**
     * Render a component into his own HTML Element.
     * @param instance {Component}
     * @param template {string}
     * @param recursive {boolean}
     * @param state {object}
     */
    static render(instance, template, state = {}, recursive = false) {

        if(recursive)
            $(instance.elem).html(instance.renderedHTML = template);
        else
            $(instance.elem).html((instance.renderedHTML = $renderTemplate(instance, template, state)));


        for(const bind of instance.binds) {

            $(bind.selector).html(bind.value
                .split(/[.\[\]'"]/)
                .filter(i => i)
                .reduce((p, v) => p ? p[v] : '', state));

        }


        const recursive_render = (elem) => {

            if(!elem)
                throw new Error('elem cannot be null for ' + instance.id);

            for(let el of elem.children) {

                if(window.components[el.id])
                    Component.render(window.components[el.id], window.components[el.id].renderedHTML, window.components[el.id].state, true);

                else if(window.registered[el.localName])
                    Component.run(el);

                else
                    recursive_render(el);

            }
        };

        recursive_render(instance.elem);

    }



    /**
     * Initialize and load all registered components or a single component.
     * @param element {string | HTMLElement | Document}
     */
    static run(element = undefined) {

        /**
         * @param e {HTMLElement}
         */
        const init = (e) => {

            if(window.components[e.id]) {

                console.debug("Skipping component, already loaded: ", window.components[e.id].id);

            } else {

                let props = {};

                if (e.hasAttributes()) {
                    for (let attr of e.attributes) {
                        if (attr.name.startsWith('ui:'))
                            props[attr.name.slice(3)] = attr.value || true;
                    }
                }

                window.registered[e.localName](e, props);

                console.debug("Loading component: ", window.components[e.id].id);

            }

        }

        if(element !== undefined) {

            init($(element).get(0));

        } else {

            for (let component of Object.keys(window.registered))
                $(component).each((i, v) => init($(v).get(0)));

        }

    }

    /**
     * Unload all components.
     */
    static destroy() {

        console.debug("Unloading all components");

        for(const component of window.components)
            component.onDestroy();

        window.components = [];

    }


    /**
     * Create a dummy component for a dummy context in a dummy world.
     * @param id {string}
     * @returns {Component}
     */
    static dummy(id = Math.random().toString(36).substring(7)) {

        const ref = 'dummy-' + id;

        if(window.components[ref])
            return window.components[ref];


        $(document.body)
            .append('<ui-dummy id="' + ref + '"></ui-dummy>');


        console.assert(document.getElementById(ref) !== undefined, 'ref cannot be null', ref);
        console.debug("Loading dummy component: ", ref);

        return (window.components[ref] = new StatelessComponent(document.getElementById(ref), {}));

    }


}


/**
 * Static UI Component with a immutable state.
 */
class StatelessComponent extends Component {

    /**
     * @param elem {HTMLElement}    HTML Element
     * @param state {object}        Component State
     */
    constructor(elem, state) {

        super(elem);
        this.onInit();

        Component.render(this, this.onLoading(), state || {});

        if((state || {}).then) {
            state.then(
                (response) => {
                    Component.render(this, this.onRender(), response);
                    this.onReady(response);
                },
                (reason) => Component.render(this, this.onError(), {})
            );
        } else {
            Component.render(this, this.onRender(), state || {});
            this.onReady(state || {});
        }

    }

    onReady() {
        this.raise('ready');
    }

}

/**
 * Dynamic UI Component with a mutable state.
 */
class StatefulComponent extends Component {

    /**
     * @param elem {HTMLElement}    HTML Element
     * @param state {object}        Component State
     */
    constructor(elem, state) {

        super(elem);
        this.onInit();

        this.$currentState = {};
        this.$isReady = false;


        Component.render(this, this.onLoading(), state || {});

        if((state || {}).then) {
            state.then(
                (response) => this.setState(response),
                (reason) => Component.render(this, this.onError(), this.state)
            )
        } else {
            this.setState(state || {});
        }

    }

    /**
     * Change state of component and redraw it.
     * @param state {object}
     * @param redraw {boolean}
     */
    setState(state, redraw = true) {

        this.$currentState = Object.assign(this.$currentState, state);


        this.onBeforeUpdate(this.state);

        if(redraw)
            Component.render(this, this.onRender(), this.state);

        this.onUpdated(this.state);


        if(!this.$isReady) {
            this.$isReady = true;
            this.onReady(this.state);
        }

    }

    /**
     * Get current state of a dynamic UI component.
     * @returns {object}
     */
    get state() {
        return this.$currentState || {};
    }

    /**
     * Change state of component and redraw it.
     * @param state {object}
     * @see setState
     */
    set state(state) {
        this.setState(state);
    }


    /**
     * Before redraw of a component.
     * @param state {object}
     */
    onBeforeUpdate(state) {
        this.raise('before-update');
    }

    /**
     * After redraw of a component.
     * @param state {object}
     */
    onUpdated(state) {
        this.raise('updated');
    }

    /**
     * When a first state is available
     * @param state {object}
     */
    onReady(state) {
        this.raise('ready');
    }

}


/**
 * Render a template string in HTML.
 * @param instance {Component}
 * @param template {string}
 * @param state {object}
 * @returns {string}
 */
const $renderTemplate = (instance, template = '', state = {}) => {

    /**
     * @param id {string}
     * @param snippet {string}
     * @returns {string}
     */
    const sanitize = (id, snippet) => {
        return snippet
            .replace(/(")/gm, "\\\"")
            .replace(/(\r\n|\n|\r)/gm, "")
            .replace(/{{/gm, "\" + (")
            .replace(/}}/gm, ") + \"")
            .replace(/\$\$/gm, "window.components['" + id + "']");
    }


    let index = 0;
    let output = '';
    let regexp = /<\?(.*?)?\?>/gs;


    output += 'let $$$$ = [];\n';

    let match;
    while ((match = regexp.exec(template))) {

        if(match[0] === undefined)
            break;


        output += `$$$$.push("${sanitize(instance.id, template.substr(index, match.index - index))}");\n`;
        output += `${match[1]}\n`;

        index = regexp.lastIndex;

    }

    output += `$$$$.push("${sanitize(instance.id, template.substr(index, template.length - index))}");\n`;
    output += 'return $$$$.join("");';


    try {

        return new Function(Object.keys(state).join(", "), output)
            .apply(instance, Object.values(state));

    } catch (e) {
        console.error(e);
        console.error(output);
    }

}
