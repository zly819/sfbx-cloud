/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.itheima.sfbx.framework.rule.parse.scorecard;

import com.itheima.sfbx.framework.rule.model.scorecard.CustomCol;
import com.itheima.sfbx.framework.rule.parse.Parser;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2016年9月22日
 */
public class CustomColParser implements Parser<CustomCol> {
	@Override
	public CustomCol parse(Element element) {
		CustomCol col=new CustomCol();
		col.setColNumber(Integer.parseInt(element.attributeValue("col-number")));
		col.setName(element.attributeValue("name"));
		col.setWidth(element.attributeValue("width"));
		return col;
	}
	@Override
	public boolean support(String name) {
		return name.equals("custom-col");
	}
}
