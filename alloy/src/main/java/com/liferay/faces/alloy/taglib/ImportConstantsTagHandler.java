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
package com.liferay.faces.alloy.taglib;

import javax.faces.view.facelets.TagConfig;

import com.liferay.faces.alloy.taghandler.ImportConstants;


/**
 * @author      Neil Griffin
 * @deprecated  Replaced by {@link ImportConstants}.
 */
@Deprecated
public class ImportConstantsTagHandler extends ImportConstants {

	public ImportConstantsTagHandler() throws Exception {
		super(new JspTagConfig());
	}

	public ImportConstantsTagHandler(TagConfig config) throws Exception {
		super(config);
	}
}
