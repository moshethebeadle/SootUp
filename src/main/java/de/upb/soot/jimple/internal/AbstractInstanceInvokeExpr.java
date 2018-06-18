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


package de.upb.soot.jimple.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.upb.soot.core.SootMethodRef;
import de.upb.soot.jimple.Value;
import de.upb.soot.jimple.ValueBox;
import de.upb.soot.jimple.expr.InstanceInvokeExpr;

@SuppressWarnings("serial")
public abstract class AbstractInstanceInvokeExpr extends AbstractInvokeExpr 
                      implements InstanceInvokeExpr
{
    final protected ValueBox baseBox;    
    
    protected AbstractInstanceInvokeExpr(SootMethodRef methodRef, ValueBox baseBox, ValueBox[] argBoxes) {
    	super(methodRef, argBoxes);
    	this.baseBox = baseBox;
    }
    
    @Override
    public Value getBase()
    {
        return baseBox.getValue();
    }

    @Override
    public ValueBox getBaseBox()
    {
        return baseBox;
    }

    @Override
    public void setBase(Value base)
    {
        baseBox.setValue(base);
    }

    @Override
    public List<ValueBox> getUseBoxes()
    {
        List<ValueBox> list = new ArrayList<ValueBox>();
        if (argBoxes != null) {
        	Collections.addAll(list, argBoxes);
	        for (ValueBox element : argBoxes) {
	            list.addAll(element.getValue().getUseBoxes());            
	        }
        }
        list.addAll(baseBox.getValue().getUseBoxes());
        list.add(baseBox);
        
        return list;
    }
}
