/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Patrick Lam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package de.upb.soot.jimple.javabyte;

import de.upb.soot.StmtPrinter;
import de.upb.soot.jimple.Jimple;
import de.upb.soot.jimple.basic.Value;
import de.upb.soot.jimple.basic.ValueBox;
import de.upb.soot.jimple.common.stmt.AbstractStmt;
import de.upb.soot.jimple.visitor.IStmtVisitor;
import de.upb.soot.jimple.visitor.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class JRetStmt extends AbstractStmt {
  final ValueBox stmtAddressBox;
  // List useBoxes;

  public JRetStmt(Value stmtAddress) {
    this(Jimple.getInstance().newLocalBox(stmtAddress));
  }

  protected JRetStmt(ValueBox stmtAddressBox) {
    this.stmtAddressBox = stmtAddressBox;

  }

  @Override
  public Object clone() {
    return new JRetStmt(Jimple.cloneIfNecessary(getStmtAddress()));
  }

  @Override
  public String toString() {
    return Jimple.RET + " " + stmtAddressBox.getValue().toString();
  }

  @Override
  public void toString(StmtPrinter up) {
    up.literal(Jimple.RET);
    up.literal(" ");
    stmtAddressBox.toString(up);
  }

  public Value getStmtAddress() {
    return stmtAddressBox.getValue();
  }

  public ValueBox getStmtAddressBox() {
    return stmtAddressBox;
  }

  public void setStmtAddress(Value stmtAddress) {
    stmtAddressBox.setValue(stmtAddress);
  }

  @Override
  public List<ValueBox> getUseBoxes() {
    List<ValueBox> useBoxes = new ArrayList<ValueBox>();

    useBoxes.addAll(stmtAddressBox.getValue().getUseBoxes());
    useBoxes.add(stmtAddressBox);

    return useBoxes;
  }

  @Override
  public void accept(IVisitor sw) {
    ((IStmtVisitor) sw).caseRetStmt(this);
  }

  @Override
  public boolean fallsThrough() {
    return true;
  }

  @Override
  public boolean branches() {
    return false;
  }

}
