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
package com.itheima.sfbx.framework.rule.builder;

import com.itheima.sfbx.framework.rule.builder.resource.Resource;
import com.itheima.sfbx.framework.rule.builder.resource.ResourceBuilder;
import com.itheima.sfbx.framework.rule.builder.resource.ResourceType;
import com.itheima.sfbx.framework.rule.builder.table.DecisionTableRulesBuilder;
import com.itheima.sfbx.framework.rule.builder.table.ScriptDecisionTableRulesBuilder;
import com.itheima.sfbx.framework.rule.dsl.DSLRuleSetBuilder;
import com.itheima.sfbx.framework.rule.model.decisiontree.DecisionTree;
import com.itheima.sfbx.framework.rule.model.flow.FlowDefinition;
import com.itheima.sfbx.framework.rule.model.library.ResourceLibrary;
import com.itheima.sfbx.framework.rule.model.rete.Rete;
import com.itheima.sfbx.framework.rule.model.rete.builder.ReteBuilder;
import com.itheima.sfbx.framework.rule.model.rule.Library;
import com.itheima.sfbx.framework.rule.model.rule.Rule;
import com.itheima.sfbx.framework.rule.model.rule.RuleSet;
import com.itheima.sfbx.framework.rule.model.rule.lhs.Lhs;
import com.itheima.sfbx.framework.rule.model.rule.loop.LoopRule;
import com.itheima.sfbx.framework.rule.model.scorecard.runtime.ScoreRule;
import com.itheima.sfbx.framework.rule.model.table.DecisionTable;
import com.itheima.sfbx.framework.rule.model.table.ScriptDecisionTable;
import com.itheima.sfbx.framework.rule.runtime.KnowledgePackageWrapper;
import com.itheima.sfbx.framework.rule.runtime.service.KnowledgePackageService;
import org.dom4j.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2014年12月22日
 */
public class KnowledgeBuilder extends AbstractBuilder{
	private ResourceLibraryBuilder resourceLibraryBuilder;
	private ReteBuilder reteBuilder;
	private RulesRebuilder rulesRebuilder;
	private DecisionTreeRulesBuilder decisionTreeRulesBuilder;
	private DecisionTableRulesBuilder decisionTableRulesBuilder;
	private ScriptDecisionTableRulesBuilder scriptDecisionTableRulesBuilder;
	private DSLRuleSetBuilder dslRuleSetBuilder;
	public static final String BEAN_ID="urule.knowledgeBuilder";
	public KnowledgeBase buildKnowledgeBase(ResourceBase resourceBase) throws IOException{
		KnowledgePackageService knowledgePackageService=(KnowledgePackageService)applicationContext.getBean(KnowledgePackageService.BEAN_ID);
		List<Rule> rules=new ArrayList<Rule>();
		Map<String,Library> libMap=new HashMap<String,Library>();
		Map<String,FlowDefinition> flowMap=new HashMap<String,FlowDefinition>();
		for(Resource resource:resourceBase.getResources()){
			if(dslRuleSetBuilder.support(resource)){
				RuleSet ruleSet=dslRuleSetBuilder.build(resource.getContent());
				addToLibraryMap(libMap,ruleSet.getLibraries());
				if(ruleSet.getRules()!=null){
					rules.addAll(ruleSet.getRules());
				}
				continue;
			}
			Element root=parseResource(resource.getContent());
			for(ResourceBuilder<?> builder:resourceBuilders){
				if(!builder.support(root)){
					continue;
				}
				Object object=builder.build(root);
				ResourceType type=builder.getType();
				if(type.equals(ResourceType.RuleSet)){
					RuleSet ruleSet=(RuleSet)object;
					addToLibraryMap(libMap,ruleSet.getLibraries());
					if(ruleSet.getRules()!=null){
						List<Rule> ruleList=ruleSet.getRules();
						rulesRebuilder.convertNamedJunctions(ruleList);
						for(Rule rule:ruleList){
							if(rule.getEnabled()!=null && rule.getEnabled()==false){
								continue;
							}
							rules.add(rule);							
						}
					}
				}else if(type.equals(ResourceType.DecisionTree)){
					DecisionTree tree=(DecisionTree)object;
					addToLibraryMap(libMap,tree.getLibraries());
					RuleSet ruleSet=decisionTreeRulesBuilder.buildRules(tree);
					addToLibraryMap(libMap,ruleSet.getLibraries());
					if(ruleSet.getRules()!=null){
						rules.addAll(ruleSet.getRules());							
					}
				}else if(type.equals(ResourceType.DecisionTable)){
					DecisionTable table=(DecisionTable)object;
					addToLibraryMap(libMap,table.getLibraries());
					List<Rule> tableRules=decisionTableRulesBuilder.buildRules(table);
					rules.addAll(tableRules);
				}else if(type.equals(ResourceType.ScriptDecisionTable)){
					ScriptDecisionTable table=(ScriptDecisionTable)object;
					RuleSet ruleSet=scriptDecisionTableRulesBuilder.buildRules(table);
					addToLibraryMap(libMap,ruleSet.getLibraries());
					if(ruleSet.getRules()!=null){
						rules.addAll(ruleSet.getRules());
					}
				}else if(type.equals(ResourceType.Flow)){
					FlowDefinition fd=(FlowDefinition)object;
					fd.initNodeKnowledgePackage(this, knowledgePackageService, dslRuleSetBuilder);
					addToLibraryMap(libMap,fd.getLibraries());
					flowMap.put(fd.getId(), fd);
				}else if(type.equals(ResourceType.Scorecard)){
					ScoreRule rule=(ScoreRule)object;
					rules.add(rule);
					addToLibraryMap(libMap,rule.getLibraries());
				}
				break;
			}
		}
		ResourceLibrary resourceLibrary=resourceLibraryBuilder.buildResourceLibrary(libMap.values());
		buildLoopRules(rules, resourceLibrary);
		Rete rete=reteBuilder.buildRete(rules, resourceLibrary);
		return new KnowledgeBase(rete,flowMap,retriveNoLhsRules(rules));
	}
	
	private void buildLoopRules(List<Rule> rules,ResourceLibrary resourceLibrary){
		for(Rule rule:rules){
			if(!(rule instanceof LoopRule)){
				continue;
			}
			LoopRule loopRule=(LoopRule)rule;
			List<Rule> ruleList=buildRules(loopRule);
			Rete rete=reteBuilder.buildRete(ruleList, resourceLibrary);
			KnowledgeBase base=new KnowledgeBase(rete);
			KnowledgePackageWrapper knowledgeWrapper=new KnowledgePackageWrapper(base.getKnowledgePackage());
			loopRule.setKnowledgePackageWrapper(knowledgeWrapper);
		}
	}
	
	private List<Rule> buildRules(LoopRule loopRule){
		Rule rule=new Rule();
		rule.setDebug(loopRule.getDebug());
		rule.setName("loop-rule");
		rule.setLhs(loopRule.getLhs());
		rule.setRhs(loopRule.getRhs());
		rule.setOther(loopRule.getOther());
		List<Rule> rules=new ArrayList<Rule>();
		rules.add(rule);
		return rules;
	}
	public KnowledgeBase buildKnowledgeBase(RuleSet ruleSet){
		List<Rule> rules=new ArrayList<Rule>();
		Map<String,Library> libMap=new HashMap<String,Library>();
		addToLibraryMap(libMap,ruleSet.getLibraries());
		if(ruleSet.getRules()!=null){
			rules.addAll(ruleSet.getRules());
		}
		ResourceLibrary resourceLibrary=resourceLibraryBuilder.buildResourceLibrary(libMap.values());
		Rete rete=reteBuilder.buildRete(rules, resourceLibrary);
		return new KnowledgeBase(rete,null,retriveNoLhsRules(rules));
	}
	
	private List<Rule> retriveNoLhsRules(List<Rule> rules) {
		List<Rule> noLhsRules=new ArrayList<Rule>();
		for(Rule rule:rules){
			Lhs lhs=rule.getLhs();
			if((rule instanceof LoopRule) || (lhs==null || lhs.getCriterion()==null)){
				noLhsRules.add(rule);
			}
		}
		return noLhsRules;
	}
	
	private void addToLibraryMap(Map<String,Library> map,List<Library> libraries){
		if(libraries==null){
			return;
		}
		for(Library lib:libraries){
			String path=lib.getPath();
			if(map.containsKey(path)){
				continue;
			}
			map.put(path, lib);
		}
	}
	
	public void setRulesRebuilder(RulesRebuilder rulesRebuilder) {
		this.rulesRebuilder = rulesRebuilder;
	}
	
	public void setReteBuilder(ReteBuilder reteBuilder) {
		this.reteBuilder = reteBuilder;
	}
	public void setDecisionTableRulesBuilder(DecisionTableRulesBuilder decisionTableRulesBuilder) {
		this.decisionTableRulesBuilder = decisionTableRulesBuilder;
	}
	public void setScriptDecisionTableRulesBuilder(ScriptDecisionTableRulesBuilder scriptDecisionTableRulesBuilder) {
		this.scriptDecisionTableRulesBuilder = scriptDecisionTableRulesBuilder;
	}
	public void setDslRuleSetBuilder(DSLRuleSetBuilder dslRuleSetBuilder) {
		this.dslRuleSetBuilder = dslRuleSetBuilder;
	}
	public void setResourceLibraryBuilder(ResourceLibraryBuilder resourceLibraryBuilder) {
		this.resourceLibraryBuilder = resourceLibraryBuilder;
	}
	public void setDecisionTreeRulesBuilder(DecisionTreeRulesBuilder decisionTreeRulesBuilder) {
		this.decisionTreeRulesBuilder = decisionTreeRulesBuilder;
	}
}
