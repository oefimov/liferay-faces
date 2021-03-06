/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.component.inputfile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.bridge.component.inputfile.internal.InputFileDelegationResponseWriter;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.map.ContextMapFactory;
import com.liferay.faces.bridge.event.FileUploadEvent;
import com.liferay.faces.bridge.model.internal.UploadedFileBridgeImpl;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.render.DelegationResponseWriter;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = InputFile.COMPONENT_FAMILY, rendererType = InputFile.RENDERER_TYPE)
//J+
public class InputFileRenderer extends InputFileRendererBase {

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		InputFile inputFile = (InputFile) uiComponent;

		Map<String, List<UploadedFile>> uploadedFileMap = getUploadedFileMap(facesContext);

		if (uploadedFileMap != null) {

			String clientId = uiComponent.getClientId(facesContext);
			List<UploadedFile> uploadedFiles = uploadedFileMap.get(clientId);

			if ((uploadedFiles != null) && (uploadedFiles.size() > 0)) {

				inputFile.setSubmittedValue(uploadedFiles);

				// Queue the FileUploadEvent so that each uploaded file can be handled individually with an
				// ActionListener.
				for (UploadedFile uploadedFile : uploadedFiles) {

					com.liferay.faces.bridge.model.UploadedFile bridgeUploadedFile = new UploadedFileBridgeImpl(
							uploadedFile);
					FileUploadEvent fileUploadEvent = new FileUploadEvent(uiComponent, bridgeUploadedFile);
					uiComponent.queueEvent(fileUploadEvent);
				}
			}
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		DelegationResponseWriter delegationResponseWriter = new InputFileDelegationResponseWriter(responseWriter);
		super.encodeEnd(facesContext, uiComponent, delegationResponseWriter);
	}

	@Override
	public String getDelegateComponentFamily() {
		return InputFile.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return InputFile.DELEGATE_RENDERER_TYPE;
	}

	protected Map<String, List<UploadedFile>> getUploadedFileMap(FacesContext facesContext) {

		ContextMapFactory contextMapFactory = (ContextMapFactory) FactoryExtensionFinder.getFactory(
				ContextMapFactory.class);
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return contextMapFactory.getUploadedFileMap(bridgeContext);
	}
}
