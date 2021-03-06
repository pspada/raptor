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
package org.createnet.raptor.action;

import java.util.List;
import org.createnet.raptor.models.data.ActionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Luca Capra <lcapra@fbk.eu>
 */
public interface ActionStatusRepository extends MongoRepository<ActionStatus, String> {
    public ActionStatus findOneByDeviceIdAndActionId(String deviceId, String actionId);
    public List<ActionStatus> findByDeviceId(String deviceId, String actionId);
}
