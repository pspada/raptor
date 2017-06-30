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
package org.createnet.raptor.tree;

import org.createnet.raptor.api.common.dispatcher.RaptorMessageHandler;
import org.createnet.raptor.models.payload.DevicePayload;
import org.createnet.raptor.models.payload.DispatcherPayload;
import org.createnet.raptor.models.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luca Capra <lcapra@fbk.eu>
 */
public class TreeMessageHandler implements RaptorMessageHandler {
    
    final Logger log = LoggerFactory.getLogger(TreeMessageHandler.class);
    
    @Autowired
    TreeService treeService;
    
    @Override
    public void handle(DispatcherPayload dispatcherPayload) {

        switch (dispatcherPayload.getType()) {
            case device:

                DevicePayload payload = (DevicePayload) dispatcherPayload;
                
                TreeNode node = treeService.get(payload.device.id());
                
                //constrined reactions
                switch (payload.op) {
                    case delete:
                        if(node != null) {
                            treeService.delete(payload.device.id());
                        }
                        break;
                    case update:
                        if(node != null) {
                            node.name(payload.device.name());
                            treeService.save(node);
                        }
                        break;
                }
                
                break;
        }

    }
    
    protected void notifyParent(TreeNode node) {
        
        TreeNode parents = treeService.parents(node);
        
        TreeNode parent = parents;
        while(parent != null) {
            
            
            
            parent = parents.getParent();
        }
        
    }

}
