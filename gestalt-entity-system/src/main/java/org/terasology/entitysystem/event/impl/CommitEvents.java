/*
 * Copyright 2015 MovingBlocks
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

package org.terasology.entitysystem.event.impl;

import org.terasology.entitysystem.transaction.pipeline.TransactionContext;
import org.terasology.entitysystem.transaction.pipeline.TransactionInterceptor;

/**
 *
 */
public class CommitEvents implements TransactionInterceptor {

    private AbstractEventSystem eventSystem;

    CommitEvents(AbstractEventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }

    @Override
    public void handle(TransactionContext context) {
        context.getAttachment(EventState.class).ifPresent((x) -> {
            for (PendingEventInfo pendingEvent : x.getPendingEvents()) {
                eventSystem.processEvent(pendingEvent.getEvent(), pendingEvent.getEntity(), pendingEvent.getTriggeringComponents());
            }
        });
    }
}
