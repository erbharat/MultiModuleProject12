;This file will be executed next to the application bundle image
;I.e. current directory will contain folder ExpenseTracker with application files
[Setup]
AppId={{fxApplication}}
AppName=ExpenseTracker
AppVersion=1.0
AppVerName=ExpenseTracker 1.2
AppPublisher=BK Tech
AppComments=UiBuilder
AppCopyright=Copyright (C) 2015
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName=C:\ExpenseTracker
DisableStartupPrompt=No
DisableDirPage=No
DisableProgramGroupPage=No
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=No
DefaultGroupName=BK Tech
;Optional License
LicenseFile=License.rtf
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=ExpenseTracker-1.2
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=ExpenseTracker\ExpenseTracker.ico
UninstallDisplayIcon={app}\ExpenseTracker.ico
UninstallDisplayName=ExpenseTracker
WizardImageStretch=No
WizardSmallImageFile=ExpenseTracker-setup-icon.BMP 
ArchitecturesInstallIn64BitMode=x64

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "ExpenseTracker\ExpenseTracker.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "ExpenseTracker\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\ExpenseTracker"; Filename: "{app}\ExpenseTracker.exe"; IconFilename: "{app}\ExpenseTracker.ico"; Check: returnTrue()
Name: "{commondesktop}\ExpenseTracker"; Filename: "{app}\ExpenseTracker.exe";  IconFilename: "{app}\ExpenseTracker.ico"; Check: returnFalse()

[Run]
Filename: "{app}\ExpenseTracker.exe"; Description: "{cm:LaunchProgram,ExpenseTracker}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\ExpenseTracker.exe"; Parameters: "-install -svcName ""ExpenseTracker"" -svcDesc ""ExpenseTracker"" -mainExe ""ExpenseTracker.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\ExpenseTracker.exe "; Parameters: "-uninstall -svcName ExpenseTracker -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
