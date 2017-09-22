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
package org.createnet.raptor.models.payload;

import org.createnet.raptor.models.OperationName;
import org.createnet.raptor.models.OperationType;
import org.createnet.raptor.models.auth.Permission;

/**
 *
 * @author Luca Capra <luca.capra@fbk.eu>
 */
public class AbstractPayload implements DispatcherPayload {
    
    public OperationType type;
    public OperationName op;
    
    @Override
    public OperationType getType() {
        return type;
    }

    @Override
    public OperationName getOp() {
        return op;
    }
    
}
