/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.usergrid.rest.test.resource2point0.endpoints.mgmt;


import javax.ws.rs.core.MediaType;

import org.apache.usergrid.rest.test.resource2point0.endpoints.NamedResource;
import org.apache.usergrid.rest.test.resource2point0.endpoints.UrlResource;
import org.apache.usergrid.rest.test.resource2point0.model.ApiResponse;
import org.apache.usergrid.rest.test.resource2point0.model.Token;
import org.apache.usergrid.rest.test.resource2point0.model.User;
import org.apache.usergrid.rest.test.resource2point0.state.ClientContext;


/**
 * Called by the ManagementResource. This contains anything token related that comes back to the ManagementResource.
 */
public class TokenResource extends NamedResource {
    public TokenResource( final ClientContext context, final UrlResource parent ) {
        super( "token", context, parent );
    }


    /**
     * Obtains an access token of type "application user"
     * @param grant
     * @param username
     * @param password
     * @return
     */
    public Token post(Token token){

        return getResource().type( MediaType.APPLICATION_JSON_TYPE )
                                    .accept( MediaType.APPLICATION_JSON ).post(Token.class,token);
//        ApiResponse response = getResource().type( MediaType.APPLICATION_JSON_TYPE )
//                            .accept( MediaType.APPLICATION_JSON ).post(ApiResponse.class,token);

//        Token returnedToken = new Token( response );
//        returnedToken.setUser(new User(response));
        //return returnedToken;
    }

}
