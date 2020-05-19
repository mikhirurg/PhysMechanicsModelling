import os
print('Building project')
os.system("jpackage --input \"out/artifacts/PhysMechanicsModelling_jar\" --name \"PhysModeling\" --main-jar \"PhysMechanicsModelling.jar\" --main-class "
    "io.github.mikhirurg.physlab1.PhysicsDemo --type exe --win-shortcut --win-menu --win-per-user-install")
