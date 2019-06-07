.class public class_test2
.super java/lang/Object
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
ldc 5
istore 1
ldc 0
istore 2
WHILELABEL0:
iload 1
ldc 1
ineg
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
ldc 0
istore 2
iload 1
ldc 2
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
WHILELABEL8:
iload 2
ldc 7
if_icmplt LTLABEL10
iconst_0
goto LTCOMPLETE11
LTLABEL10:
iconst_1
LTCOMPLETE11:
ifeq ENDWHILELABEL9
iload 2
ldc 5
if_icmpne EQUALLABEL12
iconst_1
goto EQUALCOMPLETE13
EQUALLABEL12:
iconst_0
EQUALCOMPLETE13:
ifeq ELSELABEL14
goto ENDWHILELABEL9
goto CONDITIONCOMPLETE15
ELSELABEL14:
CONDITIONCOMPLETE15:
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 1
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 2
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "*"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
iload 2
iconst_1
iadd
istore 2
goto WHILELABEL8
ENDWHILELABEL9:
goto WHILELABEL0
ENDWHILELABEL1:
ldc 0
ireturn
.end method
