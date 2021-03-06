/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.codeInsight.codeTransformations;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.plugin.codeInsight.codeTransformations.AbstractCodeTransformationTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@InnerTestClasses({CodeTransformationsTestGenerated.IfStatementWithAssignmentsToExpression.class, CodeTransformationsTestGenerated.AssignmentWithIfExpressionToStatement.class, CodeTransformationsTestGenerated.RemoveUnnecessaryParentheses.class})
public class CodeTransformationsTestGenerated extends AbstractCodeTransformationTest {
    @TestMetadata("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression")
    public static class IfStatementWithAssignmentsToExpression extends AbstractCodeTransformationTest {
        public void testAllFilesPresentInIfStatementWithAssignmentsToExpression() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("innerIfTransformed.kt")
        public void testInnerIfTransformed() throws Exception {
            doTestIfStatementWithAssignmentsToExpression("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression/innerIfTransformed.kt");
        }
        
        @TestMetadata("nestedIfs.kt")
        public void testNestedIfs() throws Exception {
            doTestIfStatementWithAssignmentsToExpression("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression/nestedIfs.kt");
        }
        
        @TestMetadata("simpleIf.kt")
        public void testSimpleIf() throws Exception {
            doTestIfStatementWithAssignmentsToExpression("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression/simpleIf.kt");
        }
        
        @TestMetadata("simpleIfWithoutElse.kt")
        public void testSimpleIfWithoutElse() throws Exception {
            doTestIfStatementWithAssignmentsToExpression("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression/simpleIfWithoutElse.kt");
        }
        
        @TestMetadata("simpleIfWithoutTerminatingAssignment.kt")
        public void testSimpleIfWithoutTerminatingAssignment() throws Exception {
            doTestIfStatementWithAssignmentsToExpression("idea/testData/codeInsight/codeTransformations/ifStatementWithAssignmentsToExpression/simpleIfWithoutTerminatingAssignment.kt");
        }
        
    }
    
    @TestMetadata("idea/testData/codeInsight/codeTransformations/assignmentWithIfExpressionToStatement")
    public static class AssignmentWithIfExpressionToStatement extends AbstractCodeTransformationTest {
        public void testAllFilesPresentInAssignmentWithIfExpressionToStatement() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/codeInsight/codeTransformations/assignmentWithIfExpressionToStatement"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("innerIfTransformed.kt")
        public void testInnerIfTransformed() throws Exception {
            doTestAssignmentWithIfExpressionToStatement("idea/testData/codeInsight/codeTransformations/assignmentWithIfExpressionToStatement/innerIfTransformed.kt");
        }
        
        @TestMetadata("nestedIfs.kt")
        public void testNestedIfs() throws Exception {
            doTestAssignmentWithIfExpressionToStatement("idea/testData/codeInsight/codeTransformations/assignmentWithIfExpressionToStatement/nestedIfs.kt");
        }
        
        @TestMetadata("simpleIf.kt")
        public void testSimpleIf() throws Exception {
            doTestAssignmentWithIfExpressionToStatement("idea/testData/codeInsight/codeTransformations/assignmentWithIfExpressionToStatement/simpleIf.kt");
        }
        
        @TestMetadata("simpleIfWithoutAssignment.kt")
        public void testSimpleIfWithoutAssignment() throws Exception {
            doTestAssignmentWithIfExpressionToStatement("idea/testData/codeInsight/codeTransformations/assignmentWithIfExpressionToStatement/simpleIfWithoutAssignment.kt");
        }
        
    }
    
    @TestMetadata("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses")
    public static class RemoveUnnecessaryParentheses extends AbstractCodeTransformationTest {
        public void testAllFilesPresentInRemoveUnnecessaryParentheses() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("necessaryParentheses1.kt")
        public void testNecessaryParentheses1() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/necessaryParentheses1.kt");
        }
        
        @TestMetadata("necessaryParentheses2.kt")
        public void testNecessaryParentheses2() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/necessaryParentheses2.kt");
        }
        
        @TestMetadata("necessaryParentheses3.kt")
        public void testNecessaryParentheses3() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/necessaryParentheses3.kt");
        }
        
        @TestMetadata("necessaryParentheses4.kt")
        public void testNecessaryParentheses4() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/necessaryParentheses4.kt");
        }
        
        @TestMetadata("necessaryParentheses5.kt")
        public void testNecessaryParentheses5() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/necessaryParentheses5.kt");
        }
        
        @TestMetadata("unnecessaryParentheses1.kt")
        public void testUnnecessaryParentheses1() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses1.kt");
        }
        
        @TestMetadata("unnecessaryParentheses2.kt")
        public void testUnnecessaryParentheses2() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses2.kt");
        }
        
        @TestMetadata("unnecessaryParentheses3.kt")
        public void testUnnecessaryParentheses3() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses3.kt");
        }
        
        @TestMetadata("unnecessaryParentheses4.kt")
        public void testUnnecessaryParentheses4() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses4.kt");
        }
        
        @TestMetadata("unnecessaryParentheses5.kt")
        public void testUnnecessaryParentheses5() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses5.kt");
        }
        
        @TestMetadata("unnecessaryParentheses6.kt")
        public void testUnnecessaryParentheses6() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses6.kt");
        }
        
        @TestMetadata("unnecessaryParentheses7.kt")
        public void testUnnecessaryParentheses7() throws Exception {
            doTestRemoveUnnecessaryParentheses("idea/testData/codeInsight/codeTransformations/removeUnnecessaryParentheses/unnecessaryParentheses7.kt");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("CodeTransformationsTestGenerated");
        suite.addTestSuite(IfStatementWithAssignmentsToExpression.class);
        suite.addTestSuite(AssignmentWithIfExpressionToStatement.class);
        suite.addTestSuite(RemoveUnnecessaryParentheses.class);
        return suite;
    }
}
