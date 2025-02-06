package sootup.callgraph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import sootup.core.signatures.MethodSignature;
import sootup.java.core.views.JavaView;

/**
 * Input source examples taken from <a href="https://bitbucket.org/delors/cats/src/master/">cats</a>
 *
 * @author Jacob Toronto
 */
@Tag("Java8")
public class RelaxedClassHierarchyAnalysisAlgorithmTest
    extends CallGraphTestBase<RelaxedClassHierarchyAnalysisAlgorithm> {

  // TODO: StaticInitializers, Lambdas ?

  @Override
  protected RelaxedClassHierarchyAnalysisAlgorithm createAlgorithm(JavaView view) {
    return new RelaxedClassHierarchyAnalysisAlgorithm(view);
  }

  /**
   * Test that proves that it includes interfaces.
   * TODO -- all the other tests still don't quite work.
   */
  @Test
  public void testDynamicInterfaceMethod0_modified() {
    CallGraph cg = loadCallGraph("InterfaceMethod", false, "j8dim0.Class");
    MethodSignature interfaceMethod =
        identifierFactory.getMethodSignature(
            identifierFactory.getClassType("j8dim0.Interface"),
            "method",
            "void",
            Collections.emptyList());
    MethodSignature classMethod =
        identifierFactory.getMethodSignature(
            identifierFactory.getClassType("j8dim0.Class"),
            "method",
            "void",
            Collections.emptyList());
    assertTrue(
        cg.containsCall(
            mainMethodSignature,
            interfaceMethod,
            getInvokableStmt(mainMethodSignature, interfaceMethod)));
    assertTrue(
        cg.containsCall(
            mainMethodSignature,
            classMethod,
            getInvokableStmt(mainMethodSignature, interfaceMethod)));
  }
}
