package com.jaspersoft.studio.server.protocol.restv2;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;
import com.jaspersoft.studio.utils.Misc;

public class RESTv2ExceptionHandler {
	private ARestV2Connection c;
	private Map<String, String> map;

	public RESTv2ExceptionHandler(ARestV2Connection c) {
		this.c = c;
	}

	private Map<String, String> getMap(IProgressMonitor monitor) {
		if (map == null) {
			map = new HashMap<String, String>();
			c.getBundle(map, "jasperserver_messages", monitor);
		}
		return map;
	}

	public void handleException(Response res, IProgressMonitor monitor) throws ClientProtocolException {
		String msg = "";
		int status = res.getStatus();
		switch (status) {
		case 400:
			if (res.getHeaderString("Content-Type").contains("xml")) {
				List<ErrorDescriptor> list = res.readEntity(new GenericType<List<ErrorDescriptor>>() {
				});
				if (list != null) {
					msg = "";
					for (ErrorDescriptor ed : list)
						msg += buildMessage(monitor, msg, ed);
					throw new HttpResponseException(status, msg);
				}
			}
		case 401:
			throw new HttpResponseException(status, res.getStatusInfo().getReasonPhrase());
		case 403:
		case 409:
		case 404:
		case 500:
			if (res.getHeaderString("Content-Type").contains("xml")) {
				ErrorDescriptor ed = res.readEntity(ErrorDescriptor.class);
				msg = buildMessage(monitor, "", ed);
				if (!ed.getErrorCode().contains("{0}") && ed.getParameters() != null)
					for (String str : ed.getParameters())
						msg += "\n" + str;
				throw new HttpResponseException(status, msg);
			}
		default:
			String cnt = res.readEntity(String.class);
			if (cnt.length() > 100)
				cnt = "";
			msg = res.getStatusInfo().getReasonPhrase() + "\n" + cnt;
			throw new HttpResponseException(status, msg);
		}
	}

	protected String buildMessage(IProgressMonitor monitor, String msg, ErrorDescriptor ed) {
		if (!msg.isEmpty())
			msg += "\n";
		if (ed.getMessage() != null) {
			if (ed.getParameters() != null)
				msg += MessageFormat.format(ed.getMessage(), (Object[]) ed.getParameters());
			else
				msg += ed.getMessage();
		} else {
			String m = getMap(monitor).get(ed.getErrorCode());
			if (Misc.isNullOrEmpty(m))
				msg += ed.getErrorCode();
			else
				msg += MessageFormat.format(m, (Object[]) ed.getParameters());
		}
		return msg;
	}
}
