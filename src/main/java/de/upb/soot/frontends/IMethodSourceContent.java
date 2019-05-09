package de.upb.soot.frontends;

import de.upb.soot.core.Body;
import de.upb.soot.core.SootMethod;
import de.upb.soot.signatures.MethodSignature;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrick Lam
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

/** A class which knows how to produce Body's for SootMethods. */
public interface IMethodSourceContent {

  // TODO Maybe we should also remove the SootMethod parameter here.
  //  See comment in IClassSourceContent for details.

  /** Returns a filled-out body for the given SootMethod. */
  @Nullable
  Body resolveBody(@Nonnull SootMethod m) throws ResolveException;

  // FIXME: [JMP] This method is never used
  @Nonnull
  MethodSignature getSignature();
}
