package de.upb.swt.soot;

import static org.junit.Assert.*;

import de.upb.swt.soot.core.frontend.SootClassSource;
import de.upb.swt.soot.core.model.SootClass;
import de.upb.swt.soot.core.model.SourceType;
import de.upb.swt.soot.core.util.printer.Printer;
import java.io.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;

public class JimpleVisitorImplTest {

  void checkJimpleClass(CharStream cs) {

    JimpleVisitorImpl parser = new JimpleVisitorImpl();
    final SootClassSource scs = parser.parse(cs);

    StringWriter output = new StringWriter();
    Printer p = new Printer();
    final SootClass sc = new SootClass(scs, SourceType.Application);
    p.printTo(sc, new PrintWriter(output));

    System.out.println(output);

    /*

    final List<SootDiff.DiffItem> diff = SootDiff.diff(sc, );
    if( !diff.isEmpty()){
      SootDiff.print(diff);
    }
    assertTrue(diff.isEmpty());
    */
  }

  @Test
  public void parseMinimalClass() {

    CharStream cs = CharStreams.fromString("class MinClass \n { }");
    checkJimpleClass(cs);
  }

  @Test
  public void parseEmptyClass() {
    CharStream cs =
        CharStreams.fromString(
            "public class EmptyClass extends java.lang.Object\n" + " { " + " \n " + "  " + "} ");
    checkJimpleClass(cs);
  }

  @Test
  public void parseClassImplements() {
    CharStream cs1 =
        CharStreams.fromString(
            "public class Developer implements human.interaction.devices.Typing \n { public void <init>(){}"
                + "private void another(){}  } ");
    checkJimpleClass(cs1);

    CharStream cs2 =
        CharStreams.fromString(
            "public class Developer implements human.interaction.devices.Typing, human.system.KeepAwake \n { public void <init>(){} } ");
    checkJimpleClass(cs2);
  }

  @Test
  public void parseClassExtends() {
    CharStream cs =
        CharStreams.fromString(
            "public class BigTable extends Small.Table \n { public void <init>(){}"
                + "private void another(){}  } ");
    checkJimpleClass(cs);
  }

  @Test
  public void testImports() {
    CharStream cs =
        CharStreams.fromString(
            "import Small.Table; \n"
                + "import Huge.BigTable; \n"
                + "public class BigTable extends Table \n { public void <init>(){}"
                + "private void another(){}  } ");
    checkJimpleClass(cs);
  }

  @Test
  public void testValidDuplicateImports() {
    CharStream cs =
        CharStreams.fromString(
            "import Small.Table; \n"
                + "import Small.Table; \n"
                + "public class BigTable extends Table \n { public void <init>(){}"
                + "private void another(){}  } ");
    checkJimpleClass(cs);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidDuplicateImports() {
    CharStream cs =
        CharStreams.fromString(
            "import Small.Table; \n"
                + "import Medium.Table; \n"
                + "public class BigTable extends Table \n { public void <init>(){}"
                + "private void another(){}  } ");
    checkJimpleClass(cs);
  }

  @Test
  public void parseClassWField() {
    CharStream cs =
        CharStreams.fromString(
            "public class StaticFieldClass extends java.lang.Object \n  { static bool globalCounter;  } ");
    checkJimpleClass(cs);

    CharStream cs1 =
        CharStreams.fromString(
            "public class StaticFieldClass extends java.lang.Object \n  { bool $flag;  } ");
    checkJimpleClass(cs1);

    CharStream cs2 =
        CharStreams.fromString(
            "public class StaticFieldClass extends java.lang.Object \n  { java.lang.String globalCounter;  } ");
    checkJimpleClass(cs2);

    CharStream cs3 =
        CharStreams.fromString(
            "public class StaticFieldClass extends java.lang.Object \n  { int globalCounter;  } ");
    checkJimpleClass(cs3);
  }

  @Test
  public void parseClassWFields() {
    CharStream cs =
        CharStreams.fromString(
            "public class InstanceField extends java.lang.Object\n"
                + " {     public int globalCounter;\n long sth;} ");
    checkJimpleClass(cs);
  }

  @Test
  public void parseClassMethodWOBody() {
    CharStream cs =
        CharStreams.fromString(
            "public class Noclass extends java.lang.Object \n { public abstract void nobody();  } ");
    checkJimpleClass(cs);

    CharStream cs2 =
        CharStreams.fromString(
            "public class Noclass extends java.lang.Object \n { public native void withoutbody();  } ");
    checkJimpleClass(cs2);
  }

  @Test
  public void parseClassWMethod() {
    CharStream cs =
        CharStreams.fromString(
            "public class EmptyClass extends java.lang.Object \n { public void <init>(){}  } ");
    checkJimpleClass(cs);
  }

  @Test
  public void parseClassWMethods() {
    CharStream cs =
        CharStreams.fromString(
            "public class EmptyClass extends java.lang.Object \n { public void <init>(){}"
                + "private void another(){}  } ");

    checkJimpleClass(cs);
  }

  @Test
  public void parseInterleavingClassMembers() {
    CharStream cs1 =
        CharStreams.fromString(
            "public class Interleaving1 extends java.lang.Object \n { public void <init>(){} static int globalCounter; protected void another(){}  } ");
    checkJimpleClass(cs1);

    CharStream cs2 =
        CharStreams.fromString(
            "public class Interleaving2 extends java.lang.Object \n { private bool flag; void <init>(){} static int globalCounter; protected void another(){}  } ");
    checkJimpleClass(cs2);
  }

  @Test
  public void parseMethodWithParameter() {
    CharStream cs1 =
        CharStreams.fromString(
            "public class Param extends java.lang.Object \n { public void <init>(java.lang.String){} \n void another(int, float, double, bool, java.lang.String){}  } ");
    checkJimpleClass(cs1);
  }

  @Test
  public void testSinglelineComment() {
    CharStream cs =
        CharStreams.fromString(
            "//SingleLine One \n"
                + "// SecondSingleLine \n"
                + "import Small.Table; \n"
                + "// SingleLine Two \n"
                + "public class BigTable extends Table \n {"
                + "// SingleLine One \n"
                + "public void <init>(){}"
                + "// SingleLine One \n"
                + " }"
                + "// SingleLine End");
    checkJimpleClass(cs);
  }

  @Test
  public void testLineComment() {
    CharStream cs =
        CharStreams.fromString(
            "import Medium.Table; \n"
                + "public class //Chair extends Table \n"
                + "BigTable extends Table \n {"
                + "public void <init>(){} \n"
                + "//private void another(){} \n"
                + "} ");
    checkJimpleClass(cs);
  }

  @Test
  public void testSimpleLongComment() {
    CharStream cs =
        CharStreams.fromString(
            "/* One */ /**/ /* ** * //* ** / */"
                + "import Medium.Table; \n"
                + "public class BigTable extends Table \n {"
                + " public void <init>(){} \n"
                + "private void another(){} \n"
                + "} \n");
    checkJimpleClass(cs);
  }

  @Test
  public void testLongCommentEverywhere() {
    CharStream cs =
        CharStreams.fromString(
            "/*One*/ \n"
                + "/*One */ \n"
                + "/* One*/ \n"
                + "import /*Comment*//*more */Medium.Table; \n"
                + "/* \n Two */"
                + "public /* Crumble*/ class // nope // no \n /* a class destroys it*/ BigTable extends Table \n {"
                // + "\n /*\n  Three \n \n */"
                + " public void <init>(){}"
                + "private void another(){}  "
                //    + "/* Another opening /* */"
                //   + "/* \n End \n */"
                + "} ");
    checkJimpleClass(cs);
  }

  @Test
  public void testInvoke() {
    // FIXME!!
    CharStream cs =
        CharStreams.fromString(
            "class Invoke{"
                + "public void <init>()\n"
                + "    {\n"
                + "      de.upb.soot.instructions.expr.DynamicInvokeExprTest r0;\n"
                + "      r0 := @this: de.upb.soot.instructions.expr.DynamicInvokeExprTest;\n"
                + "      specialinvoke r0.<java.lang.Object: void <init>()>();\n"
                + "      return;\n"
                + "    }"
                + "}");
    checkJimpleClass(cs);
  }

  @Test
  public void testOther() {

    CharStream cs =
        CharStreams.fromString(
            "class de.upb.soot.concrete.fieldReference.A extends java.lang.Object\n"
                + "  {\n"
                + "  static int it;\n"
                + "    public java.lang.String j;\n"
                + "    void <init>()\n"
                + "    {\n"
                + "      de.upb.soot.concrete.fieldReference.A r0;\n"
                + "      r0 := @this: de.upb.soot.concrete.fieldReference.A;\n"
                + "      specialinvoke r0.<java.lang.Object: void <init>()>();\n"
                + "      r0.<de.upb.soot.concrete.fieldReference.A: int i> = 15;\n"
                + "      r0.<de.upb.soot.concrete.fieldReference.A: java.lang.String j> = \"greater\";\n"
                + "      return;\n"
                + "    }\n"
                + "  }\n"
                + "\n");
    checkJimpleClass(cs);
  }
}
