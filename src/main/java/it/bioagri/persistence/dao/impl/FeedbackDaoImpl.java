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
import it.bioagri.models.Feedback;
import it.bioagri.persistence.DataSource;
import it.bioagri.persistence.dao.FeedbackDao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FeedbackDaoImpl extends FeedbackDao {

    public FeedbackDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<Feedback> findByPrimaryKey(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Feedback> findAll() throws SQLException {

        var feedbacks = new LinkedList<Feedback>();


        try(var connection = getDataSource().getConnection()) {

            var statement = connection.prepareStatement("SELECT * FROM shop_feedback");
            var result = statement.executeQuery();

            if(result.next()) {

                feedbacks.add(new Feedback(
                        result.getLong("id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getFloat("vote"),
                        result.getTimestamp("created_at"),
                        result.getTimestamp("updated_at")
                ));

            }

        }


        return feedbacks;

    }

    @Override
    public void save(Feedback value) throws SQLException {

    }

    @Override
    public void update(Feedback value, Object... params) throws SQLException {

    }

    @Override
    public void delete(Feedback value) throws SQLException {

    }
}
