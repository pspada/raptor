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

import org.createnet.raptor.common.dispatcher.events.ActionApplicationEvent;
import org.createnet.raptor.events.Event;
import org.createnet.raptor.events.type.ActionEvent;
import org.createnet.raptor.models.data.ActionStatus;
import org.createnet.raptor.models.objects.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luca Capra <lcapra@fbk.eu>
 */
@Service
public class ActionStatusEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void notify(Action action, ActionStatus status, Event.EventType type) {
        
        ActionEvent actionEvent = new ActionEvent(action, status);
        actionEvent.setEvent(type.name());
        
        ActionApplicationEvent ev = new ActionApplicationEvent(this, actionEvent);
        applicationEventPublisher.publishEvent(ev);
    }
    
    public void execute(Action action, ActionStatus status) {
        notify(action, status, Event.EventType.execute);
    }
    
    public void delete(Action action, ActionStatus status) {
        notify(action, status, Event.EventType.deleteAction);
    }

}
