.class public class_test2
.super java/lang/Object
.field public t Lclass_test2;
.field public j I
.method public <init>()V
.limit locals 100
.limit stack 10000
aload_0
invokespecial java/lang/Object/<init>()V
aload_0
iconst_0
putfield class_test2/j I
return
.end method
.method public main()I
.limit locals 100
.limit stack 10000
ldc 10
istore 1
WHILELABEL0:
iload 1
ldc 0
if_icmpgt GTLABEL2
iconst_0
goto GTCOMPLETE3
GTLABEL2:
iconst_1
GTCOMPLETE3:
ifeq ENDWHILELABEL1
iload 1
iconst_1
isub
istore 1
iload 1
ldc 3
if_icmpne EQUALLABEL4
iconst_1
goto EQUALCOMPLETE5
EQUALLABEL4:
iconst_0
EQUALCOMPLETE5:
ifeq ELSELABEL6
goto WHILELABEL0
goto CONDITIONCOMPLETE7
ELSELABEL6:
CONDITIONCOMPLETE7:
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 1
invokevirtual java/io/PrintStream/println(I)V
goto WHILELABEL0
ENDWHILELABEL1:
ldc 0
ireturn
.end method
.method public k()I
.limit locals 100
.limit stack 10000
ldc 5
ireturn
.end method
