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

package it.bioagri.admin;

import it.bioagri.api.auth.AuthToken;
import it.bioagri.models.*;
import it.bioagri.persistence.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class ProductManager {

    private final AuthToken authToken;
    private final DataSource dataSource;
    private final ServletContext servletContext;

    @Autowired
    public ProductManager(AuthToken authToken, DataSource dataSource, ServletContext servletContext) {
        this.authToken = authToken;
        this.dataSource = dataSource;
        this.servletContext = servletContext;
    }


    @GetMapping("/admin/product")
    public String getAllData(ModelMap model) {

        model.addAttribute("categories", dataSource.getCategoryDao().findAll());
        model.addAttribute("tags", dataSource.getTagDao().findAll());
        return "/admin/product";

    }

    @PostMapping("/admin/create/product")
    public void save(

            HttpServletResponse response,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String info,
            @RequestParam Float price,
            @RequestParam Float discount,
            @RequestParam Integer stock,
            @RequestParam ProductStatus status,
            @RequestParam String tag,
            @RequestParam String category,
            @RequestParam String files

    ) throws IOException {


        Product product = new Product(
                dataSource.getId("shop_product", Long.class),
                name,
                description,
                info,
                price,
                discount,
                stock,
                status,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                null,
                null,
                null
        );

        dataSource.getProductDao().save(product);

        System.out.println("categorie checked:" + category);

        for(var i : category.split(","))
            dataSource.getProductDao().addCategory(product,dataSource.getCategoryDao().findByPrimaryKey(Long.parseLong(i)).get());

        for(var i : tag.split(","))
            dataSource.getProductDao().addTag(product,dataSource.getTagDao().findByPrimaryKey(Long.parseLong(i)).get());

        response.sendRedirect("/admin/dashboard");


    }

    @GetMapping("/admin/delete/product")
    public void delete(@RequestParam long id, HttpServletResponse response) throws IOException {

        dataSource.getProductDao().delete(dataSource.getProductDao().findByPrimaryKey(id).get());
        response.sendRedirect("/admin/dashboard");

    }


    @PostMapping("/admin/update/product")
    public void update(

            HttpServletResponse response,
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Float price,
            @RequestParam Integer stock,
            @RequestParam String info,
            @RequestParam Float discount,
            @RequestParam ProductStatus status,
            @RequestParam String tag,
            @RequestParam String category,
            @RequestParam String files
    ) throws IOException {


                Product product = new Product(
                        id,
                        name,
                        description,
                        info,
                        price,
                        discount,
                        stock,
                        status,
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now()),
                        null,
                        null,
                        null
                );

                dataSource.getProductDao().update(product, product);

                for(Category categoryToDelete : dataSource.getProductDao().findByPrimaryKey(id).get().getCategories(dataSource))
                    dataSource.getProductDao().removeCategory(product, categoryToDelete);

                for(Tag tagToDelete : dataSource.getProductDao().findByPrimaryKey(id).get().getTags(dataSource))
                    dataSource.getProductDao().removeTag(product, tagToDelete);


                        for (var i : category.split(",+"))
                            dataSource.getProductDao().addCategory(product, dataSource.getCategoryDao().findByPrimaryKey(Long.parseLong(i)).get());


                        for (var i : tag.split(",+"))
                            dataSource.getProductDao().addTag(product, dataSource.getTagDao().findByPrimaryKey(Long.parseLong(i)).get());


        response.sendRedirect("/admin/dashboard");


    }


}
