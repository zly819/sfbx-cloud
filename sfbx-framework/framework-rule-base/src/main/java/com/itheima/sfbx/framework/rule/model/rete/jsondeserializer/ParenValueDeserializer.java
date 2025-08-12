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
import com.itheima.sfbx.framework.rule.model.rule.ParenValue;
import com.itheima.sfbx.framework.rule.model.rule.Value;
import com.itheima.sfbx.framework.rule.model.rule.ValueType;
import org.codehaus.jackson.JsonNode;

/**
 * @author Jacky.gao
 * @since 2015年6月15日
 */
public class ParenValueDeserializer implements ValueDeserializer {

	@Override
	public Value deserialize(JsonNode jsonNode) {
		ParenValue value=new ParenValue();
		value.setArithmetic(JsonUtils.parseComplexArithmetic(jsonNode));
		value.setValue(JsonUtils.parseValue(jsonNode));
		return value;
	}

	@Override
	public boolean support(ValueType type) {
		return type.equals(ValueType.Paren);
	}

}
