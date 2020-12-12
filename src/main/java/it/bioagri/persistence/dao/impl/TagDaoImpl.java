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

import it.bioagri.models.Tag;
import it.bioagri.persistence.DataSource;
import it.bioagri.persistence.dao.TagDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TagDaoImpl extends TagDao {

    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<Tag> findByPrimaryKey(String id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Tag> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(Tag value) throws SQLException {

    }

    @Override
    public void update(Tag value, Object... params) throws SQLException {

    }

    @Override
    public void delete(Tag value) throws SQLException {

    }
}
