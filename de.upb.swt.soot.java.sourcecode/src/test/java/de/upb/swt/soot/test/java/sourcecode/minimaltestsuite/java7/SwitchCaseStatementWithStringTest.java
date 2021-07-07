/**
 * @author: Markus Schmidt
 * @author: Hasitha Rajapakse
 */
package de.upb.swt.soot.test.java.sourcecode.minimaltestsuite.java7;

import categories.Java8Test;
import de.upb.swt.soot.core.model.SootMethod;
import de.upb.swt.soot.core.signatures.MethodSignature;
import de.upb.swt.soot.test.java.sourcecode.minimaltestsuite.MinimalSourceTestSuiteBase;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(Java8Test.class)
public class SwitchCaseStatementWithStringTest extends MinimalSourceTestSuiteBase {

  @Override
  public MethodSignature getMethodSignature() {
    return identifierFactory.getMethodSignature(
        "switchCaseStatementString", getDeclaredClassSignature(), "void", Collections.emptyList());
  }

  @Override
  public List<String> expectedBodyStmts() {
    // TODO: [ms] the generated jimple is not correct: $i1,$i1,$i3 are undefined/not assigned to
    // anything
    return Stream.of(
            "r0 := @this: SwitchCaseStatementWithString",
            "$r1 = \"something\"",
            "$i0 = 0",
            "if $r1 == $i1 goto label3",
            "if $r1 == $i2 goto label2",
            "if $r1 == $i3 goto label1",
            "goto label4",
            "label1:",
            "$i0 = 3",
            "goto label5",
            "label2:",
            "$i0 = 2",
            "goto label5",
            "label3:",
            "$i0 = 1",
            "goto label5",
            "label4:",
            "$i4 = 0 - 1",
            "$i0 = $i4",
            "label5:",
            "return")
        .collect(Collectors.toList());
  }

  public MethodSignature getMethodSignature2() {
    return identifierFactory.getMethodSignature(
        "switchCaseStatementInt", getDeclaredClassSignature(), "void", Collections.emptyList());
  }

  public List<String> expectedBodyStmts2() {
    return Stream.of(
            "r0 := @this: SwitchCaseStatementWithString",
            "$i0 = 2",
            "$u0 = null",
            "switch($i0)",
            "case 1: goto label3",
            "case 2: goto label1",
            "case 3: goto label2",
            "default: goto label3",
            "label1:",
            "$u0 = \"number 2 detected\"",
            "goto label4",
            "label2:",
            "$u0 = \"number 3 detected\"",
            "goto label4",
            "label3:",
            "$u0 = \"number 1 detected\"",
            "goto label4",
            "label4:",
            "return")
        .collect(Collectors.toList());
  }

  public MethodSignature getMethodSignature3() {
    return identifierFactory.getMethodSignature(
        "switchCaseStatementEnum", getDeclaredClassSignature(), "void", Collections.emptyList());
  }

  public List<String> expectedBodyStmts3() {
    return Stream.of(
            "r0 := @this: SwitchCaseStatementWithString",
            "$r1 = \"RED\"",
            "$r2 = \"\"",
            "$r3 = staticinvoke <Color: Color valueOf(java.lang.String)>($r1)",
            "$r4 = <Color: Color RED>",
            "if $r3 == $r4 goto label2",
            "$r5 = <Color: Color GREEN>",
            "if $r3 == $r5 goto label1",
            "goto label3",
            "label1:",
            "$r7 = <Color: Color GREEN>",
            "$r2 = \"color green detected\"",
            "goto label4",
            "label2:",
            "$r6 = <Color: Color RED>",
            "$r2 = \"color red detected\"",
            "goto label4",
            "label3:",
            "$r2 = \"invalid color\"",
            "goto label4",
            "label4:",
            "return")
        .collect(Collectors.toList());
  }

  @Test
  public void test() {
    SootMethod method = loadMethod(getMethodSignature());
    assertJimpleStmts(method, expectedBodyStmts());

    SootMethod method2 = loadMethod(getMethodSignature2());
    assertJimpleStmts(method2, expectedBodyStmts2());

    SootMethod method3 = loadMethod(getMethodSignature3());
    assertJimpleStmts(method3, expectedBodyStmts3());
  }
}
