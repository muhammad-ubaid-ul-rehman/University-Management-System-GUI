$projectPath = Get-Location
$libPath = Join-Path $projectPath "lib"

if (-not (Test-Path $libPath)) {
    New-Item -ItemType Directory -Path $libPath -Force | Out-Null
}

Write-Host "Attempting to download rs2xml.jar from alternative sources..." -ForegroundColor Cyan
Write-Host ""

# Alternative sources for rs2xml.jar
$sources = @(
    @{
        Name = "SourceForge Mirror 1"
        Url = "https://sourceforge.mirrorservice.org/f/finalangelsanddemons/rs2xml.jar"
    },
    @{
        Name = "GitHub Raw (Alt)"
        Url = "https://raw.githubusercontent.com/ameenabid/DatabaseApp/master/lib/rs2xml.jar"
    },
    @{
        Name = "Maven Archive (rs2xml)"
        Url = "https://repo1.maven.org/maven2/com/amd/jittery/rs2xml/1.0/rs2xml-1.0.jar"
    }
)

$targetPath = Join-Path $libPath "rs2xml.jar"

if (Test-Path $targetPath) {
    $size = [math]::Round((Get-Item $targetPath).Length / 1MB, 2)
    Write-Host "rs2xml.jar already exists ($size MB)" -ForegroundColor Green
    exit 0
}

foreach ($source in $sources) {
    Write-Host "Trying: $($source.Name)" -ForegroundColor Yellow
    
    try {
        Invoke-WebRequest -Uri $source.Url -OutFile $targetPath -UseBasicParsing -TimeoutSec 15
        
        if (Test-Path $targetPath) {
            $size = [math]::Round((Get-Item $targetPath).Length / 1MB, 2)
            Write-Host "  SUCCESS: Downloaded ($size MB)" -ForegroundColor Green
            exit 0
        }
    } catch {
        Write-Host "  Failed: $_" -ForegroundColor Red
    }
    
    Write-Host ""
}

Write-Host "Could not download from any source." -ForegroundColor Red
Write-Host ""
Write-Host "Manual Download Instructions:" -ForegroundColor Yellow
Write-Host "1. Download rs2xml.jar from: https://sourceforge.net/projects/finalangelsanddemons/"
Write-Host "2. Or from GitHub: Search for 'rs2xml.jar implementation java'"
Write-Host "3. Place the file in: $libPath"
Write-Host ""
Write-Host "Note: rs2xml.jar is required for ResultSet to JTable conversion" -ForegroundColor Cyan
