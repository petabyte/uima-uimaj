/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.caseditor.editor.editview;

import org.apache.uima.caseditor.editor.AnnotationEditorView;
import org.apache.uima.caseditor.editor.ICasDocument;
import org.apache.uima.caseditor.editor.ICasEditor;

/**
 * TODO: add javadoc here
 */
public final class EditView extends AnnotationEditorView {
	/**
	 * The ID of the feature structure view.
	 */
	public static final String ID = "org.apache.uima.caseditor.editview";
	public static final String ID_2 = "org.apache.uima.caseditor.editview2";

	public EditView() {
		super("The edit view is currently not available.");
	}

	@Override
	protected PageRec doCreatePage(ICasEditor editor) {

		PageRec result = null;

		ICasDocument document = editor.getDocument();

		if (document != null) {
			EditViewPage page = new EditViewPage(this, editor, document);
			initPage(page);
			page.createControl(getPageBook());

			result = new PageRec(editor, page);
		}

		return result;
	}
}