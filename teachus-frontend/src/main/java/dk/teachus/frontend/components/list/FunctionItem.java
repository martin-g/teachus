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
package dk.teachus.frontend.components.list;

import java.io.Serializable;

import org.apache.wicket.Component;

public abstract class FunctionItem implements Serializable {
	private String title;
	
	public FunctionItem() {
	}

	public FunctionItem(String title) {
		this.title = title;
	}

	public abstract void onEvent(Object object);
	
	public abstract Component createLabelComponent(String wicketId, Object object);
	
	public String getClickConfirmText(Object object) {
		return null;
	}
	
	public boolean isEnabled(Object object) {
		return true;
	}
	
	public String getTitle() {
		return title;
	}
}