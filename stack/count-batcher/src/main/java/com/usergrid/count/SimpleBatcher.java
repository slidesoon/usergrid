/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.usergrid.count;

import com.usergrid.count.common.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A simple Batcher implementation that keeps a sum of the
 * number of {@link Count} operations which have been applied.
 * Counters are aggregated by name.
 *
 * @author zznate
 */
public class SimpleBatcher extends AbstractBatcher {
    private Logger log = LoggerFactory.getLogger(SimpleBatcher.class);
    private int batchSize = 500;
    private AtomicLong batchSubmissionCount = new AtomicLong();
    private boolean blockingSubmit = false;

    public SimpleBatcher(int queueSize) {
        super(queueSize);
    }

    /**
     * Submit the batch if we have more than batchSize insertions
     *
     */
    protected boolean maybeSubmit(Batch batch) {
        int localCallCount = batch.getLocalCallCount();
        if ( batchSize == 0 || (localCallCount > 0 && localCallCount % batchSize == 0) ) {
            log.debug("submit triggered...");
            Future f = batchSubmitter.submit(batch);
            if ( batchSize == 0) {
              try {
                f.get();
              } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
              } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
              }
            }
            batchSubmissionCount.incrementAndGet();
            return true;
        }
        return false;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public long getBatchSubmissionCount() {
        return batchSubmissionCount.get();
    }

    public void setBlockingSubmit(boolean blockingSubmit) {
      this.blockingSubmit = blockingSubmit;
    }
}
