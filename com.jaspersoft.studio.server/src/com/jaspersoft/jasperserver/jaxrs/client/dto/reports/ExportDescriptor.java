/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "export")
public class ExportDescriptor {

    private String id;
    private String status;
    private String pages;
    private String attachmentsPrefix;
    private OutputResourceDescriptor outputResource;
    private ErrorDescriptor errorDescriptor;
    private List<AttachmentDescriptor> attachments;

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public void setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAttachmentsPrefix() {
        return attachmentsPrefix;
    }

    public void setAttachmentsPrefix(String attachmentsPrefix) {
        this.attachmentsPrefix = attachmentsPrefix;
    }

    public OutputResourceDescriptor getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(OutputResourceDescriptor outputResource) {
        this.outputResource = outputResource;
    }

    @XmlElementWrapper(name = "attachments")
    @XmlElement(name = "attachment", type = AttachmentDescriptor.class)
    public List<AttachmentDescriptor> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDescriptor> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportDescriptor that = (ExportDescriptor) o;

        if (attachments != null ? !attachments.equals(that.attachments) : that.attachments != null) return false;
        if (attachmentsPrefix != null ? !attachmentsPrefix.equals(that.attachmentsPrefix) : that.attachmentsPrefix != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (outputResource != null ? !outputResource.equals(that.outputResource) : that.outputResource != null)
            return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (attachmentsPrefix != null ? attachmentsPrefix.hashCode() : 0);
        result = 31 * result + (outputResource != null ? outputResource.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportDescriptor{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", pages='" + pages + '\'' +
                ", attachmentsPrefix='" + attachmentsPrefix + '\'' +
                ", outputResource=" + outputResource +
                ", attachments=" + attachments +
                '}';
    }

}
