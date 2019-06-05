.class public class_test1
.super java/lang/Object
.field private gg [I
.field public isInF Z
.method public <init>()V
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public f()Ljava/lang/String;
.limit locals 100
.limit stack 1000
aload_0
invokevirtual class_test1/g()Ljava/lang/String;
areturn
.end method
.method public g()Ljava/lang/String;
.limit locals 100
.limit stack 1000
aload_0
ldc 5
newarray int
putfield class_test1/gg [I
aload_0
getfield class_test1/gg [I
ldc 1
ldc 1387
iastore
ldc "shakiba khar khob ast"
areturn
.end method
.method public loop(I)Z
.limit locals 100
.limit stack 1000
ldc 0
istore_2
WHILELABEL0:
iload_2
ldc 10
if_icmplt LTLABEL2
iconst_0
goto LTCOMPLETE3
LTLABEL2:
iconst_1
LTCOMPLETE3:
ifeq ENDWHILELABEL1
iload_2
iload_1
if_icmpne EQUALLABEL4
iconst_1
goto EQUALCOMPLETE5
EQUALLABEL4:
iconst_0
EQUALCOMPLETE5:
ifeq ELSELABEL6
goto ENDWHILELABEL1
goto CONDITIONCOMPLETE7
ELSELABEL6:
CONDITIONCOMPLETE7:
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_2
invokevirtual java/io/PrintStream/println(I)V
iload_2
iconst_1
iadd
istore_2
goto WHILELABEL0
ENDWHILELABEL1:
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_2
invokevirtual java/io/PrintStream/println(I)V
iload_2
ldc 10
if_icmpne EQUALLABEL8
iconst_1
goto EQUALCOMPLETE9
EQUALLABEL8:
iconst_0
EQUALCOMPLETE9:
ifeq NEQUALLABEL10
iconst_0
goto NEQUALCOMPLETE11
NEQUALLABEL10:
iconst_1
NEQUALCOMPLETE11:
ifeq ELSELABEL12
iconst_0
ireturn

ELSELABEL12:
iconst_1
ireturn
CONDITIONCOMPLETE13:
.end method
