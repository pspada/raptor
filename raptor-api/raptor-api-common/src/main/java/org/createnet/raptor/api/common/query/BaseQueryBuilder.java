/*
 * Copyright 2017 FBK/CREATE-NET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.createnet.raptor.api.common.query;

import org.createnet.raptor.models.query.BaseQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Luca Capra <lcapra@fbk.eu>
 */
abstract class BaseQueryBuilder {

    public Pageable getPaging(BaseQuery query) {

        if (query.sortBy.getFields().isEmpty()) {
            return new PageRequest(query.getOffset(), query.getLimit());
        }

        return new PageRequest(
                query.getOffset(), query.getLimit(),
                new Sort(
                        Sort.Direction.valueOf(query.sortBy.getDirection()),
                        query.sortBy.getFields()
                ));
    }

}