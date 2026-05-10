$projectPath = Get-Location
$libPath = Join-Path $projectPath "lib"

if (-not (Test-Path $libPath)) {
    New-Item -ItemType Directory -Path $libPath -Force | Out-Null
    Write-Host "Created lib folder"
}

Write-Host "Downloading dependencies from Maven Central Repository..."

$deps = @(
    @{Name="mysql-connector-j-9.3.0.jar"; Url="https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.3.0/mysql-connector-j-9.3.0.jar"; Desc="MySQL JDBC Driver"},
    @{Name="jcalendar-1.4.jar"; Url="https://repo1.maven.org/maven2/com/toedter/jcalendar/1.4/jcalendar-1.4.jar"; Desc="JCalendar (JDateChooser)"}
)

foreach ($dep in $deps) {
    $targetPath = Join-Path $libPath $dep.Name
    
    if (Test-Path $targetPath) {
        Write-Host "Skipping $($dep.Name) - already exists"
    } else {
        Write-Host "Downloading $($dep.Desc)..."
        try {
            Invoke-WebRequest -Uri $dep.Url -OutFile $targetPath -UseBasicParsing -TimeoutSec 30
            if (Test-Path $targetPath) {
                $size = [math]::Round((Get-Item $targetPath).Length / 1MB, 2)
                Write-Host "  DOWNLOADED: $($dep.Name) ($size MB)"
            }
        } catch {
            Write-Host "  FAILED: $($dep.Name)"
        }
    }
}

Write-Host ""
Write-Host "Libraries in lib folder:"
$files = Get-ChildItem $libPath -Filter "*.jar" -ErrorAction SilentlyContinue
if ($files.Count -eq 0) {
    Write-Host "  (none found)"
} else {
    $files | ForEach-Object { 
        $size = [math]::Round($_.Length / 1MB, 2)
        Write-Host "  [$size MB] $($_.Name)" 
    }
}
