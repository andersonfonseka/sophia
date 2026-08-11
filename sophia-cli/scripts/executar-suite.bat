@echo off
setlocal enabledelayedexpansion

REM ============================================================
REM Sophia - Execucao da suite de testes
REM ============================================================

set "SCRIPT_DIR=%~dp0"
set "ROOT_DIR=%SCRIPT_DIR%.."

set "JAR=%ROOT_DIR%\target\sophia-cli-0.0.1-SNAPSHOT.jar"

set "PASTA=%~1"

if "%PASTA%"=="" (
    set "PASTA=%ROOT_DIR%\target\classes\teste"
)

REM ============================================================
REM Validacoes
REM ============================================================

if not exist "%JAR%" (
    echo.
    echo ERRO: JAR da Sophia nao encontrado:
    echo %JAR%
    echo.
    exit /b 1
)

if not exist "%PASTA%" (
    echo.
    echo ERRO: pasta de testes nao encontrada:
    echo %PASTA%
    echo.
    exit /b 1
)

REM ============================================================
REM Opcao verbose
REM ============================================================

set "VERBOSE="

if /I "%~2"=="--verbose" (
    set "VERBOSE=--verbose"
)

REM ============================================================
REM Contadores
REM ============================================================

set /a TOTAL=0
set /a PASSARAM=0
set /a FALHARAM=0
set /a ERROS_ESPERADOS=0

REM ============================================================
REM Inicio
REM ============================================================

echo.
echo ============================================================
echo Sophia CLI - Suite de testes
echo ============================================================
echo JAR:    %JAR%
echo TESTES: %PASTA%

if defined VERBOSE (
    echo MODO:   VERBOSE
)

echo ============================================================
echo.

REM ============================================================
REM Execucao dos testes
REM ============================================================

for /R "%PASTA%" %%F in (*.sph) do (

    set /a TOTAL+=1

    echo.
    echo ------------------------------------------------------------
    echo TESTE: %%~nxF
    echo ------------------------------------------------------------

    REM --------------------------------------------------------
    REM Identifica se e um teste de erro esperado.
    REM Testes negativos possuem nomes como:
    REM
    REM e001_variavel_nao_declarada.sph
    REM e002_funcao_inexistente.sph
    REM --------------------------------------------------------

    set "NOME=%%~nxF"
    set "ESPERADO_ERRO=0"

    if /I "!NOME:~0,1!"=="e" (
        set "ESPERADO_ERRO=1"
        set /a ERROS_ESPERADOS+=1
    )

    REM --------------------------------------------------------
    REM Executa o programa
    REM --------------------------------------------------------

    java -jar "%JAR%" executar "%%F" %VERBOSE%

    set "CODIGO=!ERRORLEVEL!"

    REM --------------------------------------------------------
    REM Avalia resultado
    REM --------------------------------------------------------

    if "!ESPERADO_ERRO!"=="1" (

        REM Teste negativo:
        REM deve ocorrer um erro.

        if "!CODIGO!"=="0" (

            set /a FALHARAM+=1

            echo [FALHOU] %%~nxF - erro esperado nao ocorreu

        ) else (

            set /a PASSARAM+=1

            echo [PASSOU] %%~nxF - erro esperado ocorreu
        )

    ) else (

        REM Teste positivo:
        REM deve executar normalmente.

        if "!CODIGO!"=="0" (

            set /a PASSARAM+=1

            echo [PASSOU] %%~nxF

        ) else (

            set /a FALHARAM+=1

            echo [FALHOU] %%~nxF - execucao retornou erro
        )
    )
)

REM ============================================================
REM Resultado
REM ============================================================

echo.
echo ============================================================
echo RESULTADO DA SUITE
echo ============================================================
echo Total:            %TOTAL%
echo Passaram:         %PASSARAM%
echo Falharam:         %FALHARAM%
echo Erros esperados:  %ERROS_ESPERADOS%
echo ============================================================
echo.

if %FALHARAM% GTR 0 (
    exit /b 1
)

exit /b 0