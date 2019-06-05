.class public class_test2
.super java/lang/Object
.method public <init>()V
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public main()I
.limit locals 100
.limit stack 1000
new class_test1
dup
invokespecial class_test1/<init>()V
astore_1
aload_1
invokevirtual class_test1/f()Ljava/lang/String;
astore_2
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_2
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_1
ldc 12
invokevirtual class_test1/loop(I)Z
ifeq ELSELABEL14
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "loop was true"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 78
invokevirtual java/io/PrintStream/println(I)V
goto CONDITIONCOMPLETE15
ELSELABEL14:
CONDITIONCOMPLETE15:
aload_1
ldc 5
invokevirtual class_test1/loop(I)Z
ifeq ANDLABEL16
aload_2
ldc "shakiba mehraban ast"
invokevirtual java/lang/String.equals(Ljava/lang/Object;)Z
ifeq ANDLABEL16
iconst_1
goto ANDCOMPLETE17
ANDLABEL16:
iconst_0
ANDCOMPLETE17:
ifeq ELSELABEL18
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "to nabayad inja biay"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto CONDITIONCOMPLETE19
ELSELABEL18:
aload_2
ldc "shakiba khar khob ast"
invokevirtual java/lang/String.equals(Ljava/lang/Object;)Z
ifeq ANDLABEL19
aload_1
ldc 5
invokevirtual class_test1/loop(I)Z
ifne NOTLABEL20
iconst_1
goto NOTCOMPLETE21
NOTLABEL20:
iconst_0
NOTCOMPLETE21:
ifeq ANDLABEL19
iconst_1
goto ANDCOMPLETE22
ANDLABEL19:
iconst_0
ANDCOMPLETE22:
ifeq ELSELABEL23
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "ebart bas dorosti ast"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto CONDITIONCOMPLETE24
ELSELABEL23:
CONDITIONCOMPLETE24:
CONDITIONCOMPLETE19:
ldc 0
ireturn
.end method
