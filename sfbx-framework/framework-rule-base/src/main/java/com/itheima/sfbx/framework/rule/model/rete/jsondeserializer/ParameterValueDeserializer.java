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
package com.itheima.sfbx.framework.rule.model.rete.jsondeserializer;

import com.itheima.sfbx.framework.rule.model.rete.JsonUtils;
import com.itheima.sfbx.framework.rule.model.rule.ParameterValue;
import com.itheima.sfbx.framework.rule.model.rule.Value;
import com.itheima.sfbx.framework.rule.model.rule.ValueType;
import org.codehaus.jackson.JsonNode;

/**
 * @author Jacky.gao
 * @since 2015年5月26日
 */
public class ParameterValueDeserializer implements ValueDeserializer {

	@Override
	public Value deserialize(JsonNode jsonNode) {
		ParameterValue value=new ParameterValue();
		value.setArithmetic(JsonUtils.parseComplexArithmetic(jsonNode));
		value.setVariableLabel(JsonUtils.getJsonValue(jsonNode, "variableLabel"));
		value.setVariableName(JsonUtils.getJsonValue(jsonNode, "variableName"));
		return value;
	}

	@Override
	public boolean support(ValueType type) {
		return type.equals(ValueType.Parameter);
	}
}
