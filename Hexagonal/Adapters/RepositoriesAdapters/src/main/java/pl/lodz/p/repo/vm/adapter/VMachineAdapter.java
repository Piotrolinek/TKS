package pl.lodz.p.repo.vm.adapter;

import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;
import pl.lodz.p.core.domain.AppleArch;
import pl.lodz.p.core.domain.MongoUUID;
import pl.lodz.p.core.domain.VMachine;
import pl.lodz.p.core.domain.x86;
import pl.lodz.p.infrastructure.vmachine.VMAdd;
import pl.lodz.p.infrastructure.vmachine.VMGet;
import pl.lodz.p.infrastructure.vmachine.VMRemove;
import pl.lodz.p.infrastructure.vmachine.VMUpdate;
import pl.lodz.p.repo.MongoUUIDEnt;
import pl.lodz.p.repo.vm.repo.VMachineRepository;
import pl.lodz.p.repo.vm.data.AppleArchEnt;
import pl.lodz.p.repo.vm.data.VMachineEnt;
import pl.lodz.p.repo.vm.data.x86Ent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
@AllArgsConstructor
public class VMachineAdapter implements VMGet, VMUpdate, VMRemove, VMAdd {
    private final VMachineRepository vMachineRepo;

    @Override
    public void add(VMachine vMachine) {
        vMachineRepo.add(convert(vMachine));
    }

    @Override
    public List<VMachine> getVMachines() {
        List<VMachine> result = new ArrayList<>();
        List<VMachineEnt> ents = vMachineRepo.getVMachines();
        for (VMachineEnt ent : ents) {
            result.add(convert(ent));
        }
        return result;
    }

    @Override
    public VMachine getVMachineByID(MongoUUID uuid) {
        return convert(vMachineRepo.getVMachineByID(convert(uuid)));
    }

    @Override
    public long size() {
        return vMachineRepo.size();
    }

    @Override
    public void remove(VMachine vMachine) {
        vMachineRepo.remove(convert(vMachine));
    }

    @Override
    public void update(MongoUUID uuid, Map<String, Object> fieldsToUpdate) {
        vMachineRepo.update(convert(uuid), fieldsToUpdate);
    }

    @Override
    public void update(MongoUUID uuid, String field, Object value) {
        vMachineRepo.update(convert(uuid), field, value);
    }

    private VMachineEnt convert(VMachine vm) {
        if (vm == null) {
            return null;
        }
        return switch (vm.getClass().getSimpleName()) {
            case "x86" -> new x86Ent(convert(vm.getEntityId()), vm.getCPUNumber(), vm.getRamSize(), vm.isRented(), ((x86)vm).getManufacturer());
            case "AppleArch" -> new AppleArchEnt(convert(vm.getEntityId()), vm.getCPUNumber(), vm.getRamSize(), vm.isRented());
            default -> throw new RuntimeException(vm.getClass().getSimpleName() + " not supported");
        };
    }

    private VMachine convert(VMachineEnt ent) {
        if (ent == null) {
            return null;
        }
        return switch (ent.getClass().getSimpleName()) {
            case "x86Ent" -> new x86(convert(ent.getEntityId()), ent.getCPUNumber(), ent.getRamSize(), ent.isRented(), ((x86Ent)ent).getManufacturer());
            case "AppleArchEnt" -> new AppleArch(convert(ent.getEntityId()), ent.getCPUNumber(), ent.getRamSize(), ent.isRented());
            default -> throw new RuntimeException(ent.getClass().getSimpleName() + " not supported");
        };
    }

    private MongoUUID convert(MongoUUIDEnt ent) {
        MongoUUID uuid = new MongoUUID();
        try {
            PropertyUtils.copyProperties(uuid, ent);
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Property copying failed: " + e);
        }
        return uuid;
    }

    private MongoUUIDEnt convert(MongoUUID uuid) {
        MongoUUIDEnt ent = new MongoUUIDEnt();
        try {
            PropertyUtils.copyProperties(ent, uuid);
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Property copying failed: " + e);
        }
        return ent;
    }
}
