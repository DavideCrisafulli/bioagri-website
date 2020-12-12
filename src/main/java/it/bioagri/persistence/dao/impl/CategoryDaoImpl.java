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

package it.bioagri.persistence.dao.impl;

import it.bioagri.models.Category;
import it.bioagri.persistence.DataSource;
import it.bioagri.persistence.dao.CategoryDao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class CategoryDaoImpl extends CategoryDao {

    public CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<Category> findByPrimaryKey(Long id) {

        final AtomicReference<Optional<Category>> result = new AtomicReference<>(Optional.empty());

        getDataSource().fetch("SELECT * FROM shop_category WHERE shop_category.id = ?",
                s -> s.setLong(1, id),
                r -> result.set(Optional.of(new Category(
                        r.getLong("id"),
                        r.getString("name")
                )))
        );

        return result.get();

    }

    @Override
    public List<Category> findAll() {

        final var categories = new LinkedList<Category>();

        getDataSource().fetch("SELECT * FROM shop_category", null,
                r -> categories.add(new Category(
                        r.getLong("id"),
                        r.getString("name")
                ))
        );

        return categories;

    }

    @Override
    public void save(Category value) {

    }

    @Override
    public void update(Category value, Object... params) {

    }

    @Override
    public void delete(Category value) {

    }

}