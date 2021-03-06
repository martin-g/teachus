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
package dk.teachus.frontend.utils;

import java.util.Date;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.joda.time.DateTime;

import dk.teachus.backend.domain.TeachUsDate;

public class DateChoiceRenderer extends ChoiceRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getDisplayValue(Object object) {
		String display = ""; //$NON-NLS-1$
		
		if (object != null) {
			if (object instanceof TeachUsDate) {
				TeachUsDate date = (TeachUsDate) object;
				display = Formatters.getFormatPrettyDate().print(date.getDateTime());
			} else if (object instanceof Date) {
				Date date = (Date) object;
				display = Formatters.getFormatPrettyDate().print(new DateTime(date));
			}
		}
		
		return display;
	}
}
