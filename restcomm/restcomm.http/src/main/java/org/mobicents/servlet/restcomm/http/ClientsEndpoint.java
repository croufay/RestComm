/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2014, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */
package org.mobicents.servlet.restcomm.http;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.Configuration;
import org.apache.shiro.authz.AuthorizationException;
import org.mobicents.servlet.restcomm.annotations.concurrency.NotThreadSafe;
import org.mobicents.servlet.restcomm.dao.AccountsDao;
import org.mobicents.servlet.restcomm.dao.ClientsDao;
import org.mobicents.servlet.restcomm.dao.DaoManager;
import org.mobicents.servlet.restcomm.entities.Client;
import org.mobicents.servlet.restcomm.entities.ClientList;
import org.mobicents.servlet.restcomm.entities.RestCommResponse;
import org.mobicents.servlet.restcomm.entities.Sid;
import org.mobicents.servlet.restcomm.http.converter.ClientConverter;
import org.mobicents.servlet.restcomm.http.converter.ClientListConverter;
import org.mobicents.servlet.restcomm.http.converter.RestCommResponseConverter;
import org.mobicents.servlet.restcomm.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

/**
 * @author quintana.thomas@gmail.com (Thomas Quintana)
 */
@NotThreadSafe
public abstract class ClientsEndpoint extends AbstractEndpoint {
    @Context
    protected ServletContext context;
    protected Configuration configuration;
    protected ClientsDao dao;
    protected Gson gson;
    protected XStream xstream;
    protected AccountsDao accountsDao;

    public ClientsEndpoint() {
        super();
    }

    @PostConstruct
    public void init() {
        final DaoManager storage = (DaoManager) context.getAttribute(DaoManager.class.getName());
        dao = storage.getClientsDao();
        accountsDao = storage.getAccountsDao();
        configuration = (Configuration) context.getAttribute(Configuration.class.getName());
        configuration = configuration.subset("runtime-settings");
        super.init(configuration);
        final ClientConverter converter = new ClientConverter(configuration);
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Client.class, converter);
        builder.setPrettyPrinting();
        gson = builder.create();
        xstream = new XStream();
        xstream.alias("RestcommResponse", RestCommResponse.class);
        xstream.registerConverter(converter);
        xstream.registerConverter(new ClientListConverter(configuration));
        xstream.registerConverter(new RestCommResponseConverter(configuration));
    }

    private Client createFrom(final Sid accountSid, final MultivaluedMap<String, String> data) {
        final Client.Builder builder = Client.builder();
        final Sid sid = Sid.generate(Sid.Type.CLIENT);
        builder.setSid(sid);
        builder.setApiVersion(getApiVersion(data));
        builder.setFriendlyName(getFriendlyName(data.getFirst("Login"), data));
        builder.setAccountSid(accountSid);
        builder.setLogin(data.getFirst("Login"));
        builder.setPassword(data.getFirst("Password"));
        builder.setStatus(getStatus(data));
        builder.setVoiceUrl(getUrl("VoiceUrl", data));
        builder.setVoiceMethod(getMethod("VoiceMethod", data));
        builder.setVoiceFallbackUrl(getUrl("VoiceFallbackUrl", data));
        builder.setVoiceFallbackMethod(getMethod("VoiceFallbackMethod", data));
        builder.setVoiceApplicationSid(getSid("VoiceApplicationSid", data));
        String rootUri = configuration.getString("root-uri");
        rootUri = StringUtils.addSuffixIfNotPresent(rootUri, "/");
        final StringBuilder buffer = new StringBuilder();
        buffer.append(rootUri).append(getApiVersion(data)).append("/Accounts/").append(accountSid.toString())
                .append("/Clients/").append(sid.toString());
        builder.setUri(URI.create(buffer.toString()));
        return builder.build();
    }

    protected Response getClient(final String accountSid, final String sid, final MediaType responseType) {
        try {
            secure(accountsDao.getAccount(accountSid), "RestComm:Read:Clients");
        } catch (final AuthorizationException exception) {
            return status(UNAUTHORIZED).build();
        }
        final Client client = dao.getClient(new Sid(sid));
        if (client == null) {
            return status(NOT_FOUND).build();
        } else {
            if (APPLICATION_XML_TYPE == responseType) {
                final RestCommResponse response = new RestCommResponse(client);
                return ok(xstream.toXML(response), APPLICATION_XML).build();
            } else if (APPLICATION_JSON_TYPE == responseType) {
                return ok(gson.toJson(client), APPLICATION_JSON).build();
            } else {
                return null;
            }
        }
    }

    protected Response getClients(final String accountSid, final MediaType responseType) {
        try {
            secure(accountsDao.getAccount(accountSid), "RestComm:Read:Clients");
        } catch (final AuthorizationException exception) {
            return status(UNAUTHORIZED).build();
        }
        final List<Client> clients = dao.getClients(new Sid(accountSid));
        if (APPLICATION_XML_TYPE == responseType) {
            final RestCommResponse response = new RestCommResponse(new ClientList(clients));
            return ok(xstream.toXML(response), APPLICATION_XML).build();
        } else if (APPLICATION_JSON_TYPE == responseType) {
            return ok(gson.toJson(clients), APPLICATION_JSON).build();
        } else {
            return null;
        }
    }

    private String getFriendlyName(final String login, final MultivaluedMap<String, String> data) {
        String friendlyName = login;
        if (data.containsKey("FriendlyName")) {
            friendlyName = data.getFirst("FriendlyName");
        }
        return friendlyName;
    }

    private int getStatus(final MultivaluedMap<String, String> data) {
        int status = Client.ENABLED;
        if (data.containsKey("Status")) {
            try {
                status = Integer.parseInt(data.getFirst("Status"));
            } catch (final NumberFormatException ignored) {
            }
        }
        return status;
    }

    public Response putClient(final String accountSid, final MultivaluedMap<String, String> data, final MediaType responseType) {
        try {
            secure(accountsDao.getAccount(accountSid), "RestComm:Create:Clients");
        } catch (final AuthorizationException exception) {
            return status(UNAUTHORIZED).build();
        }
        try {
            validate(data);
        } catch (final NullPointerException exception) {
            return status(BAD_REQUEST).entity(exception.getMessage()).build();
        }

        // Issue 109: https://bitbucket.org/telestax/telscale-restcomm/issue/109
        Client client = dao.getClient(data.getFirst("Login"));
        if (client == null) {
            client = createFrom(new Sid(accountSid), data);
            dao.addClient(client);
        }

        if (APPLICATION_XML_TYPE == responseType) {
            final RestCommResponse response = new RestCommResponse(client);
            return ok(xstream.toXML(response), APPLICATION_XML).build();
        } else if (APPLICATION_JSON_TYPE == responseType) {
            return ok(gson.toJson(client), APPLICATION_JSON).build();
        } else {
            return null;
        }
    }

    protected Response updateClient(final String accountSid, final String sid, final MultivaluedMap<String, String> data,
            final MediaType responseType) {
        try {
            secure(accountsDao.getAccount(accountSid), "RestComm:Modify:Clients");
        } catch (final AuthorizationException exception) {
            return status(UNAUTHORIZED).build();
        }
        final Client client = dao.getClient(new Sid(sid));
        if (client == null) {
            return status(NOT_FOUND).build();
        } else {
            dao.updateClient(update(client, data));
            if (APPLICATION_XML_TYPE == responseType) {
                final RestCommResponse response = new RestCommResponse(client);
                return ok(xstream.toXML(response), APPLICATION_XML).build();
            } else if (APPLICATION_JSON_TYPE == responseType) {
                return ok(gson.toJson(client), APPLICATION_JSON).build();
            } else {
                return null;
            }
        }
    }

    private void validate(final MultivaluedMap<String, String> data) throws RuntimeException {
        if (!data.containsKey("Login")) {
            throw new NullPointerException("Login can not be null.");
        } else if (!data.containsKey("Password")) {
            throw new NullPointerException("Password can not be null.");
        }
    }

    private Client update(final Client client, final MultivaluedMap<String, String> data) {
        Client result = client;
        if (data.containsKey("FriendlyName")) {
            result = result.setFriendlyName(data.getFirst("FriendlyName"));
        }
        if (data.containsKey("Password")) {
            result = result.setPassword(data.getFirst("Password"));
        }
        if (data.containsKey("Status")) {
            result = result.setStatus(getStatus(data));
        }
        if (data.containsKey("VoiceUrl")) {
            result = result.setVoiceUrl(getUrl("VoiceUrl", data));
        }
        if (data.containsKey("VoiceMethod")) {
            result = result.setVoiceMethod(getMethod("VoiceMethod", data));
        }
        if (data.containsKey("VoiceFallbackUrl")) {
            result = result.setVoiceFallbackUrl(getUrl("VoiceFallbackUrl", data));
        }
        if (data.containsKey("VoiceFallbackMethod")) {
            result = result.setVoiceFallbackMethod(getMethod("VoiceFallbackMethod", data));
        }
        if (data.containsKey("VoiceApplicationSid")) {
            result = result.setVoiceApplicationSid(getSid("VoiceApplicationSid", data));
        }
        return result;
    }
}
