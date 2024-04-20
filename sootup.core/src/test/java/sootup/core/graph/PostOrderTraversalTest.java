package sootup.core.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.StmtPositionInfo;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.expr.JAddExpr;
import sootup.core.jimple.common.expr.JLeExpr;
import sootup.core.jimple.common.stmt.*;
import sootup.core.types.PrimitiveType;

@Tag("Java8")
public class PostOrderTraversalTest {
  StmtPositionInfo noPosInfo = StmtPositionInfo.getNoStmtPositionInfo();

  @Test
  void testReversePostorderTraversal() {
    MutableBlockStmtGraph graph = createStmtGraph();
    Map<BasicBlock<?>, Integer> blockToSortedId = new HashMap<>();
    Map<BasicBlock<?>, Integer> blockToPOId = new HashMap<>();
    // assign sorted ids according to MutableBlockStmtGraph.getBlocksSorted
    int i = 0;
    for (BasicBlock<?> block : graph.getBlocksSorted()) {
      blockToSortedId.put(block, i);
      i++;
    }

    // assign po ids according to ReversePostOrderBlockTraversal.getOrder
    Iterable<BasicBlock<?>> RPO = new PostOrderBlockTraversal(graph).getOrder();
    i = 0;
    for (BasicBlock<?> block : RPO) {
      blockToPOId.put(block, i);
      i++;
    }

    Map<Integer, Integer> expectedSortedIdToRPOId = new HashMap<>();
    expectedSortedIdToRPOId.put(0, 6);
    expectedSortedIdToRPOId.put(1, 5);
    expectedSortedIdToRPOId.put(2, 0);
    expectedSortedIdToRPOId.put(3, 4);
    expectedSortedIdToRPOId.put(4, 2);
    expectedSortedIdToRPOId.put(5, 3);
    expectedSortedIdToRPOId.put(6, 1);

    for (BasicBlock<?> block : RPO) {
      Integer sortedId = blockToSortedId.get(block);
      Integer rpoId = blockToPOId.get(block);
      Integer expectedRPOId = expectedSortedIdToRPOId.get(sortedId);

      assertEquals(expectedRPOId, rpoId);
    }
  }

  private MutableBlockStmtGraph createStmtGraph() {
    MutableBlockStmtGraph graph = new MutableBlockStmtGraph();

    Local l1 = new Local("l1", PrimitiveType.IntType.getInstance());
    Local l2 = new Local("l2", PrimitiveType.IntType.getInstance());
    Local l3 = new Local("l3", PrimitiveType.IntType.getInstance());

    JAssignStmt assign01 = new JAssignStmt(l1, IntConstant.getInstance(1), noPosInfo);
    JAssignStmt assign02 = new JAssignStmt(l2, IntConstant.getInstance(2), noPosInfo);
    JAssignStmt assign03 = new JAssignStmt(l2, IntConstant.getInstance(0), noPosInfo);
    JAssignStmt assign41 = new JAssignStmt(l2, l3, noPosInfo);
    JAssignStmt assign42 =
        new JAssignStmt(l3, new JAddExpr(l3, IntConstant.getInstance(2)), noPosInfo);
    JAssignStmt assign51 = new JAssignStmt(l2, l1, noPosInfo);
    JAssignStmt assign52 =
        new JAssignStmt(l3, new JAddExpr(l3, IntConstant.getInstance(1)), noPosInfo);

    BranchingStmt if1 = new JIfStmt(new JLeExpr(l3, IntConstant.getInstance(100)), noPosInfo);
    BranchingStmt if3 = new JIfStmt(new JLeExpr(l2, IntConstant.getInstance(20)), noPosInfo);

    JReturnStmt return2 = new JReturnStmt(l2, noPosInfo);
    JGotoStmt goto4 = new JGotoStmt(noPosInfo);
    JGotoStmt goto5 = new JGotoStmt(noPosInfo);
    JGotoStmt goto6 = new JGotoStmt(noPosInfo);

    // block 0
    graph.setStartingStmt(assign01);
    graph.putEdge(assign01, assign02);
    graph.putEdge(assign02, assign03);
    graph.putEdge(assign03, if1);

    // block 1
    graph.putEdge(if1, JIfStmt.FALSE_BRANCH_IDX, return2);
    graph.putEdge(if1, JIfStmt.TRUE_BRANCH_IDX, if3);

    // block 3
    graph.putEdge(if3, JIfStmt.FALSE_BRANCH_IDX, assign41);
    graph.putEdge(if3, JIfStmt.TRUE_BRANCH_IDX, assign51);

    // block 4
    graph.putEdge(assign41, assign42);
    graph.putEdge(assign42, goto4);
    graph.putEdge(goto4, JGotoStmt.BRANCH_IDX, goto6);

    // block 5
    graph.putEdge(assign51, assign52);
    graph.putEdge(assign52, goto5);
    graph.putEdge(goto5, JGotoStmt.BRANCH_IDX, goto6);

    // block 6
    graph.putEdge(goto6, JGotoStmt.BRANCH_IDX, if1);
    return graph;
  }
}
