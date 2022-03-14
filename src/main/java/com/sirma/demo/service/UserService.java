package com.sirma.demo.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sirma.demo.exceptions.FileException;
import com.sirma.demo.model.ColleaguesDuoDTO;
import com.sirma.demo.model.UserEntity;
import com.sirma.demo.util.FileValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class UserService {
    public ColleaguesDuoDTO getLongestDuo(MultipartFile csvFile) {
        FileValidator.ValidateCSV(csvFile);

        List<UserEntity> userEntityList = parseToUserList(csvFile);
        System.out.println(Arrays.toString(userEntityList.toArray()));
        return findPairCoworkers(userEntityList);
    }

    private ColleaguesDuoDTO findPairCoworkers(List<UserEntity> userEntityList) {
        ColleaguesDuoDTO colleaguesDuoDTO = new ColleaguesDuoDTO();

        for (int i = 0; i < userEntityList.size() - 1; i++) {
            UserEntity me = userEntityList.get(i);

            for (int j = i + 1; j < userEntityList.size(); j++) {
                UserEntity you = userEntityList.get(j);
                long tempDays = 0;
                if (!arePairs(me, you)) {
                    continue;
                }
                if (me.getEmpID().equals(you.getEmpID())){
                    throw new FileException("Employee overlaps it's self on a project");
                }
                System.out.println("Comparing " + me.getEmpID() + " vs " + you.getEmpID());

                if (me.getStartDate().compareTo(you.getStartDate()) >= 0) {
                    if (you.getEndDate().compareTo(me.getEndDate()) >= 0) {
                        tempDays = DAYS.between(me.getStartDate(), me.getEndDate());
                        setCollegues(colleaguesDuoDTO, me.getEmpID(), you.getEmpID(), tempDays);
                        System.out.println("Here 3>>>> " + me.getEmpID() + " vs " + you.getEmpID());
                    } else {
                        tempDays = DAYS.between(me.getStartDate(), you.getEndDate());
                        setCollegues(colleaguesDuoDTO, me.getEmpID(), you.getEmpID(), tempDays);
                        System.out.println("Here 1>>>>> " + me.getEmpID() + " vs " + you.getEmpID());
                    }
                } else {
                    if (me.getEndDate().compareTo(you.getEndDate()) >= 0) {
                        tempDays = DAYS.between(you.getStartDate(), you.getEndDate());
                        setCollegues(colleaguesDuoDTO, me.getEmpID(), you.getEmpID(), tempDays);
                        System.out.println("Here 4>>>>" + me.getEmpID() + " vs " + you.getEmpID());
                    } else {
                        tempDays = DAYS.between(you.getStartDate(), me.getEndDate());
                        setCollegues(colleaguesDuoDTO, me.getEmpID(), you.getEmpID(), tempDays);
                        System.out.println("Here 2>>> " + me.getEmpID() + " vs " + you.getEmpID());
                    }
                }

                System.out.println("temp days: " + tempDays);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

            }
        }
        return colleaguesDuoDTO;
    }

    private void setCollegues(ColleaguesDuoDTO colleaguesDuoDTO, Long empID, Long empID1, long tempDays) {
        if (colleaguesDuoDTO.getDays() > tempDays) {
            return;
        }
        colleaguesDuoDTO.setUser1Id(empID);
        colleaguesDuoDTO.setUser2Id(empID1);
        colleaguesDuoDTO.setDays(tempDays);
    }

    private boolean arePairs(UserEntity me, UserEntity you) {
        if (me.getEndDate() == null) {
            me.setEndDate(LocalDate.now());
        }
        if (you.getEndDate() == null) {
            you.setEndDate(LocalDate.now());
        }

        if (!me.getProjectID().equals(you.getProjectID())) {
            return false;
        }
        return me.getEndDate().isAfter(you.getStartDate()) && you.getEndDate().isAfter(me.getStartDate());
    }

    private List<UserEntity> parseToUserList(MultipartFile file) {

        List<UserEntity> users;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(UserEntity.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            users = csvToBean.parse();
            System.out.println(new String(file.getBytes()));
        } catch (IOException exception) {
            throw new FileException("Error while reading file");
        }
        return users;
    }

}
