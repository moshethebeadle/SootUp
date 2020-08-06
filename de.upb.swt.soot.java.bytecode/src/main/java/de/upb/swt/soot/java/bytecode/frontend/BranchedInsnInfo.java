package de.upb.swt.soot.java.bytecode.frontend;
/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2018 Andreas Dann
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.objectweb.asm.tree.AbstractInsnNode;

// FIXME: [AD] is it reasonable to get rid of it?
final class BranchedInsnInfo {
  /* edge endpoint */
  @Nonnull private final AbstractInsnNode insn;
  /* previous stacks at edge */
  private final LinkedList<Operand[]> prevStacks;
  /* current stack at edge */
  @Nullable private List<Operand> stack;

  BranchedInsnInfo(AbstractInsnNode insn) {
    this.insn = insn;
    this.prevStacks = new LinkedList<>();
    this.stack = new ArrayList<>();
  }

  BranchedInsnInfo(AbstractInsnNode insn, List<Operand> opr) {
    this.insn = insn;
    this.prevStacks = new LinkedList<>();
    this.stack = opr;
  }

  public List<Operand> getStack() {
    return stack;
  }

  public void replaceStack(@Nullable List<Operand> stack) {
    this.stack = new ArrayList<>(stack);
  }

  @Nonnull
  public AbstractInsnNode getInsn() {
    return insn;
  }

  public LinkedList<Operand[]> getPrevStacks() {
    return prevStacks;
  }

  public void addToPrevStack(Operand[] stackss) {
    prevStacks.add(stackss);
  }
}